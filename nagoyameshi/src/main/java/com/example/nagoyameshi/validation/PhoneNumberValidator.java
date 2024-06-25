package com.example.nagoyameshi.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{2,4}-\\d{2,4}-\\d{3,4}");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return true; // これは@NotBlankに任せる
		}
		return PHONE_NUMBER_PATTERN.matcher(value).matches();
	}
}
