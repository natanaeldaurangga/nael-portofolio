package com.nael.naelportofolio.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nael.naelportofolio.validator.annotation.ValidFileExtension;
import com.nael.naelportofolio.validator.annotation.ValidFileSize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProjectPictureRequest {
	
	@NotNull
	@ValidFileExtension(allowedExtension = {"jpg", "png", "jpeg"})
	@ValidFileSize(maxSize = 1)
	private MultipartFile picture;
	
	@NotNull
	@NotEmpty
	private String name;
	
	private String description = "";
	
}
