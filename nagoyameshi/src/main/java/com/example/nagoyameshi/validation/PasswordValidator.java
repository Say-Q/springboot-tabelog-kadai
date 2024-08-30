package com.example.nagoyameshi.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String>{
	//パスワードの正規表現パターン（英小文字、英大文字、数字の組み合わせ）
	private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//nullや空白は@NotBlankに、文字数制限は@Lengthに任せる
		if(value == null || value.isEmpty()) {
			return true;
		}
		return PASSWORD_PATTERN.matcher(value).matches();
	}

}
