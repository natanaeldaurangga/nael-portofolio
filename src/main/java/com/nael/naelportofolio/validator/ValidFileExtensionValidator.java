package com.nael.naelportofolio.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.nael.naelportofolio.validator.annotation.ValidFileExtension;

import lombok.extern.slf4j.Slf4j;

//@Slf4j
public class ValidFileExtensionValidator implements ConstraintValidator<ValidFileExtension, MultipartFile> {

	private String[] allowedExtension;
	
	@Override
	public void initialize(ValidFileExtension constraintAnnotation) {
		// TODO Auto-generated method stub
		this.allowedExtension = constraintAnnotation.allowedExtension();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
//		log.info("valid file extension validator");
		boolean result = true;
		// TODO: kenapa failed to clean multipart, pelajari lagi untuk validasi multipart
        String contentType = value.getContentType();
        if (!isSupportedContentType(contentType)) {
//        	log.info(contentType);
            result = false;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("File extension is not supported")
               .addConstraintViolation();
        return result;
	}
	
	private boolean isSupportedContentType(String contentType) {
		if(this.allowedExtension.length < 1) {
			return true;
		}
		
		for(String ext : this.allowedExtension) {
//			log.info("image/" + ext + " == " + contentType + " : " + (("image/" + ext).equals(contentType)));
			if(contentType.equals("image/" + ext)) return true;
		}
		
        return false;
    }

}
