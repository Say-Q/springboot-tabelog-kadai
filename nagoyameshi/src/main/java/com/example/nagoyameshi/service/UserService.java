package com.example.nagoyameshi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;

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
}
