package com.nael.naelportofolio.dto;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProjectPictureRequest {
	
	private MultipartFile picture;
	
}
