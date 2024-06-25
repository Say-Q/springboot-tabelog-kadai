package com.example.nagoyameshi.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\d{3}-\\d{4}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;  // これは@NotBlankに任せる
        }
        return POSTAL_CODE_PATTERN.matcher(value).matches();
    }
}
