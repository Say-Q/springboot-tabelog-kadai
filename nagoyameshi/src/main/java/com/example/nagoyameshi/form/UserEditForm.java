package com.example.nagoyameshi.form;

import com.example.nagoyameshi.validation.Katakana;
import com.example.nagoyameshi.validation.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEditForm {
	@NotNull
	private Integer id;
	
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
	
}