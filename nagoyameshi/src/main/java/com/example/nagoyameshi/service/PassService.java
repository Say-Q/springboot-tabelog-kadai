package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.PasswordResetToken;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.PasswordResetTokenRepository;
import com.example.nagoyameshi.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PassService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PasswordResetTokenRepository resetTokenRepository;
	private final JavaMailSender mailSender;

	@Autowired
	public PassService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			PasswordResetTokenRepository resetTokenRepository, JavaMailSender mailSender) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.resetTokenRepository = resetTokenRepository;
		this.mailSender = mailSender;
	}

	public boolean isSamePassword(String password, String passwordConfirmat) {
		return password.equals(passwordConfirmat);
	}

	public void update(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Transactional
	public void sendResetLink(String userEmail, HttpServletRequest request) {
		User user = userRepository.findBymail(userEmail);

		if (user == null) {
			throw new IllegalArgumentException("入力されたメールアドレスは存在しません。");
		}

		//既存のトークンを削除
		resetTokenRepository.deleteByUser(user);
		
		//新しいトークン生成
		String token = UUID.randomUUID().toString();
		PasswordResetToken resetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(24));
		resetTokenRepository.save(resetToken);

		//リセットリンク生成
		String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		String resetLink = baseUrl + "/pass/newcreate?token=" + token;

		//メール送信処理
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(userEmail);
		message.setSubject("パスワード再設定リンク");
		message.setText("パスワードを再設定するには、以下のリンクをクリックしてください。:\n" + resetLink);

		mailSender.send(message);

	}

	//トークンの文字列で検索した結果を返す
	public PasswordResetToken getPasswordResetToken(String token) {
	    PasswordResetToken resetToken = resetTokenRepository.findByToken(token);
	    if (resetToken != null) {
	        System.out.println("Token found for user: " + resetToken.getUser().getMail());
	    } else {
	        System.out.println("Token not found for token: " + token);
	    }
		return resetTokenRepository.findByToken(token);
	}
	
	public void reupdate(String token, String newPassword) {
		Optional<PasswordResetToken> tokenOpt = resetTokenRepository.findById(token);
		
		User user = tokenOpt.get().getUser();
		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);
		resetTokenRepository.delete(tokenOpt.get()); // トークンを削除し、再利用を防止
	}
}
