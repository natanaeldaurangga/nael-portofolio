package com.nael.naelportofolio.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.nael.naelportofolio.validator.ValidFileSizeValidator;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFileSizeValidator.class)
public @interface ValidFileSize {

	String message() default "File size must be less than or equal to {maxSize} MB";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default{};
	
	int maxSize();
	
}
