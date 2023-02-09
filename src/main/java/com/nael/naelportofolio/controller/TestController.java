package com.nael.naelportofolio.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nael.naelportofolio.util.FileUtil;

@RestController
@RequestMapping("/api/v1/test")
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
	
	@PostMapping("/testImage")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws Exception {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uploadDir = System.getProperty("user.dir") + "/user-photos/";
		
		FileUtil.saveFile(uploadDir, fileName, file);
		
		return ResponseEntity.ok("data berhasil disimpan");
	}
	
	@GetMapping(value = "/getImage/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getImage(@PathVariable String fileName) throws IOException{
		byte[] images = Files.readAllBytes(new File(System.getProperty("user.dir") + "/user-photos/" + fileName).toPath());
		final ByteArrayResource inputStream = new ByteArrayResource(images);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(inputStream);
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
