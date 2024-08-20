package com.example.nagoyameshi.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = KatakanaValidator.class)
public @interface Katakana {
	String message() default "全角カタカナのみを入力してください。";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}
