package com.example.nagoyameshi.form;

import org.hibernate.validator.constraints.Length;

import com.example.nagoyameshi.validation.Katakana;
import com.example.nagoyameshi.validation.Password;
import com.example.nagoyameshi.validation.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {
	@NotBlank(message = "氏名を入力してください。")
	private String name;
	
	@NotBlank(message = "フリガナを入力してください。")
	@Katakana //カスタムバリデーション
	private String furigana;
	
	@NotBlank(message = "生年月日を入力してください。")
	private String birthday;
	
	@NotBlank(message = "電話番号を入力してください。")
	@PhoneNumber //カスタムバリデーション
	private String phoneNumber;
	
	@NotBlank(message = "職業を入力してください。")
	private String profession;
	
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスは正しい形式で入力してください。")
	private String mail;
	
	@NotBlank(message = "パスワードを入力してください。")
	@Password //カスタムバリデーション
	@Length(min = 8, message = "パスワードは8文字以上で入力してください。")
	private String password;
	
	@NotBlank(message = "パスワード（確認用）を入力してください。")
	@Password //カスタムバリデーション
	private String passwordConfirmation;
}
