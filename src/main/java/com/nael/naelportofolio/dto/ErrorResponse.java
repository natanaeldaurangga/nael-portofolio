package com.nael.naelportofolio.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

	private Map<String, String> errors = new HashMap<>();
	
	public static Map<String, String> extractErrorMsg(List<FieldError> fieldErrors){
		Map<String, String> result = new HashMap<>();
		fieldErrors.stream().forEach(e -> {
			result.put(e.getField(), e.getDefaultMessage());
		});
		
		return result;
	}
	
}
