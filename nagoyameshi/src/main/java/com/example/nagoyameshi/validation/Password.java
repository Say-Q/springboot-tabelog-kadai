package com.example.nagoyameshi.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
	String message() default "半角英数字で入力してください。";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
