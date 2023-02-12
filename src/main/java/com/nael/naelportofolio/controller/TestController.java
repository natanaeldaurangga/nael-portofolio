package com.nael.naelportofolio.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nael.naelportofolio.dto.AddProjectPictureRequest;
import com.nael.naelportofolio.dto.ErrorResponse;
import com.nael.naelportofolio.dto.PictureResponse;
import com.nael.naelportofolio.util.FileUtil;
import com.nael.naelportofolio.validator.annotation.ValidFileExtension;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/test")
@Slf4j
public class TestController {

	@GetMapping("/allUser")
	public ResponseEntity<String> allUser(){
		return ResponseEntity.ok("All User");
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
	public ResponseEntity<String> user(){
		return ResponseEntity.ok("user");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
	public ResponseEntity<String> admin(){
		return ResponseEntity.ok("admin");
	}

	@GetMapping("/moderator")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<String> moderator(){
		return ResponseEntity.ok("moderator");
	}

	@PostMapping(value =  "/testImage/{test}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> uploadImage(@PathVariable("test") String test, @Valid @ModelAttribute AddProjectPictureRequest request, BindingResult bindingResult) throws Exception {

		if(!bindingResult.hasErrors()) {
			log.info("bindingResult has errors");			
			FileUtil.uploadImage(request.getPicture());
			return ResponseEntity.ok("File berhasil disimpan");
		}

		return ResponseEntity.badRequest()
				.body(
						ErrorResponse.builder().errors(
								ErrorResponse.extractErrorMsg(bindingResult.getFieldErrors())
								).build()
						);
	}

	@GetMapping(value = "/getImage/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public ResponseEntity<?> getImage(@PathVariable String fileName) throws IOException{		
		PictureResponse response = FileUtil.getImage(fileName);
		if(response == null) {
			return ResponseEntity.notFound().build();
		}
		
		ByteArrayResource inputStream = response.getInputStream();
		
		return ResponseEntity.status(HttpStatus.OK).contentType(response.getMediaType()).body(inputStream);
	}

	@DeleteMapping("/deleteImage/{fileName}")
	public ResponseEntity<?> deleteImage(@PathVariable String fileName){
		File file = new File(System.getProperty("user.dir") + "/user-photos/" + fileName);
		if(!file.exists()) {
			return ResponseEntity.notFound().build();
		}

		if(!file.delete()) {
			return ResponseEntity.internalServerError().build();
		}

		return ResponseEntity.ok("File " + fileName + " berhasil dihapus.");
	}

}
