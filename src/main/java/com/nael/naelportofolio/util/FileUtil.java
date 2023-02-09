package com.nael.naelportofolio.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	public static final String IMAGE_PATH = System.getProperty("user.dir") + "/src/main/resources/images";

	public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }  
	}
	
	public static void uploadImage(MultipartFile image) throws IOException {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		
		try {
			saveFile(IMAGE_PATH, fileName, image);			
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static void deleteImage(String imageName) throws IOException {
		File file = new File(IMAGE_PATH + imageName);
		file.delete();
	}
	
}
