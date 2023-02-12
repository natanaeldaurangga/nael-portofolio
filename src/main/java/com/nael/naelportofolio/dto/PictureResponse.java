package com.nael.naelportofolio.dto;

import javax.validation.constraints.NotNull;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureResponse {

	private ByteArrayResource inputStream;
	
	private MediaType mediaType;
	
}
