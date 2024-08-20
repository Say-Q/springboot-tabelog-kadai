package com.example.nagoyameshi.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class KatakanaValidator implements ConstraintValidator<Katakana, String>{
	//カタカナの正規表現パターン
	private static final Pattern KATAKANA_PATTERN = Pattern.compile("^[\\u30A0-\\u30FF]+$");
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//nullや空白は@NotBlankに任せる
		if(value == null || value.isEmpty()) {
			return true;
		}
		
		return KATAKANA_PATTERN.matcher(value).matches();
	}

}
