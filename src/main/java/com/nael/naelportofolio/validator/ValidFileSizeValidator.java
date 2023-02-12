package com.nael.naelportofolio.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.nael.naelportofolio.validator.annotation.ValidFileSize;

public class ValidFileSizeValidator implements ConstraintValidator<ValidFileSize, MultipartFile> {

	private int maxSize = 2;
	
	@Override
	public void initialize(ValidFileSize constraintAnnotation) {
		this.maxSize = constraintAnnotation.maxSize();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
            return true;
        }

        if (value.getSize() <= maxSize * 1024 * 1024) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("File size must be less than or equal to " + maxSize + " MB")
               .addConstraintViolation();
        return false;
	}

	
}
