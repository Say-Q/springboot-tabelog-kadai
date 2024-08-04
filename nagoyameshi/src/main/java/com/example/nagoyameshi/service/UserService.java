package com.example.nagoyameshi.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		Role role = roleRepository.findByName("ROLE_FREEMEMBER");

		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setBirthday(signupForm.getBirthday());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setProfession(signupForm.getProfession());
		user.setMail(signupForm.getMail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setRole(role);
		user.setEnabled(false);

		return userRepository.save(user);
	}

	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());

		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setBirthday(userEditForm.getBirthday());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setProfession(userEditForm.getProfession());
		user.setMail(userEditForm.getMail());

		userRepository.save(user);
	}

	//メールアドレスが登録済みかどうかをチェックする
	public boolean isEmailRegistered(String mail) {
		User user = userRepository.findBymail(mail);
		return user != null;
	}

	//パスワードとパスワード（確認用）の入力値が一致するかどうかチェック
	public boolean isSamePassword(String password, String passwordConfirmat) {
		return password.equals(passwordConfirmat);
	}

	//ユーザーを有効にする
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

	//メールアドレスが変更されたかどうかをチェックする
	public boolean isEmailChanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getMail().equals(currentUser.getMail());
	}

	@Transactional
	public void upgradeRole(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		Role role = roleRepository.findByName("ROLE_PAYMEMBER");

		user.setRole(role);
		userRepository.save(user);

		// Spring Security コンテキストに新しいロールを反映
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
				AuthorityUtils.createAuthorityList("ROLE_PAYMEMBER"));
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		// デバッグログ
		System.out
				.println("Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
		System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		//セッションに変更を反映
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

		// セッション情報をデバッグログに出力
		SecurityContext context = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		System.out.println("Session Authenticated: " + context.getAuthentication().isAuthenticated());
		System.out.println("Session Authorities: " + context.getAuthentication().getAuthorities());
	}

	@Transactional
	public void downgradeRole(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		Role role = roleRepository.findByName("ROLE_FREEMEMBER");

		user.setRole(role);
		userRepository.save(user);

		// Spring Security コンテキストに新しいロールを反映
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
				AuthorityUtils.createAuthorityList("ROLE_PAYMEMBER"));
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		// デバッグログ
		System.out
				.println("Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
		System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		//セッションに変更を反映
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

		// セッション情報をデバッグログに出力
		SecurityContext context = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		System.out.println("Session Authenticated: " + context.getAuthentication().isAuthenticated());
		System.out.println("Session Authorities: " + context.getAuthentication().getAuthorities());
	}
}
