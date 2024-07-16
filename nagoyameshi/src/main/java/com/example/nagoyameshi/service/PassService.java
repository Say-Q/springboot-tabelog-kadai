package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.PasswordResetToken;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.PasswordResetTokenRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class PassService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private JavaMailSender mailSender;
	private PasswordResetTokenRepository resetTokenRepository;

	public PassService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public boolean isSamePassword(String password, String passwordConfirmat) {
		return password.equals(passwordConfirmat);
	}

	public void update(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Transactional
	public void sendResetLink(String userEmail) {
		User user = userRepository.findBymail(userEmail);

		if (user == null) {
			throw new IllegalArgumentException("入力されたメールアドレスは存在しません。");
		}

		//トークン生成
		String token = UUID.randomUUID().toString();
		PasswordResetToken resetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(24));

		resetTokenRepository.save(resetToken);

		//リセットリンク生成
		String resetLink = "http://localhost:8080/pass/reset-form?token=" + token;

		//メール送信処理
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(userEmail);
		message.setSubject("パスワード再設定リンク");
		message.setText("パスワードを再設定するには、以下のリンクをクリックしてください。:\n[リンク]");

		mailSender.send(message);
	}

	public boolean validatePasswordResetToken(String token) {
		Optional<PasswordResetToken> tokenOpt = resetTokenRepository.findById(token);
		if (tokenOpt.isEmpty() || tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
			return false;
		}
		return true;
	}
}
