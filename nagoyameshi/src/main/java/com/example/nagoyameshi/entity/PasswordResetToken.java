package com.example.nagoyameshi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PasswordResetToken {
	@Id
	private String token;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(nullable = false)
	private LocalDateTime expiryDate;

	public PasswordResetToken() {

	}

	public PasswordResetToken(String token, User user, LocalDateTime expiryDate) {
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}
}
