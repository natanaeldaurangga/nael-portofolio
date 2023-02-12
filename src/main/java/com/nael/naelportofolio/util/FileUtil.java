package com.nael.naelportofolio.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.

import com.nael.naelportofolio.dto.PictureResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	public static final String IMAGE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\";

	public static String saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
		String uniqueName = UUID.randomUUID().toString().replace('-', '_') + "." + getFileExtension(fileName);
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadPath.resolve(uniqueName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {        
			throw ioe;
		}
		
		return uniqueName;
	}

	public static String uploadImage(MultipartFile image) throws IOException {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		return saveFile(IMAGE_PATH, fileName, image);	
	}

	public static void deleteImage(String imageName) throws IOException {
		File file = new File(IMAGE_PATH + imageName);
		file.delete();
	}

	public static PictureResponse getImage(String imageName) throws IOException {
		File file = new File(IMAGE_PATH + imageName);
		//		log.info();
		if(!file.exists()) {
			log.info("file is not exists on : " + IMAGE_PATH + imageName);
			return null;
		}
		byte[] images = Files.readAllBytes(file.toPath());
		final ByteArrayResource inputStream = new ByteArrayResource(images);
		return PictureResponse.builder()
				.inputStream(inputStream)
				.mediaType(parseExtToMediaType(imageName))
				.build();
	}

	public static String getFileExtension(String fileName) {
		int pointIdx = fileName.lastIndexOf(".");
		if(pointIdx < 0) return null;
		String result = fileName.substring(pointIdx + 1);
		if(result.equals("jpg")) {
			result = "jpeg";
		}
		return result;
	}

	public static MediaType parseExtToMediaType(String fileName) {
		String ext = "image/" + getFileExtension(fileName);
		return MediaType.parseMediaType(ext);
	}

	//	public static mediaType

}
