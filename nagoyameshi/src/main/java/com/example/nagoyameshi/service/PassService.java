package com.example.nagoyameshi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class PassService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

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
}
