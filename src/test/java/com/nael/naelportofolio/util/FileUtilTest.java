package com.nael.naelportofolio.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

	@Test
	void contextLoad() {
		String fileName = "nael.jpg";
		String ext = FileUtil.getFileExtension(fileName);
		assertEquals("jpg", ext);
	}
	
	@Test
	void testParseToMediaType() {
		String fileName = "nael.png";
		MediaType mt = FileUtil.parseExtToMediaType(fileName);
		assertEquals(MediaType.IMAGE_JPEG, mt);
	}
	
	@Test
	void cekStringSplitter() {
		String fileName = "nael.png";
		assertEquals("png", fileName.split("\\.")[1]);
	}
	
}
