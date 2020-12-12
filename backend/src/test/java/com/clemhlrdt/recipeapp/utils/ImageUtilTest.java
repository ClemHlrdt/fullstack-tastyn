package com.clemhlrdt.recipeapp.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilTest {

	private static final String STATIC_FOLDER = System.getProperty("user.dir") + "/static/";

	@BeforeAll
	public static void init(){

		List<String> lines = Arrays.asList("The first line", "The second line");
		Path file = Paths.get(STATIC_FOLDER + "test-file.jpg");
		Path file2 = Paths.get(STATIC_FOLDER + "test-file2.jpg");
		Path file3 = Paths.get(STATIC_FOLDER + "test-file3.jpg");
		try {
			Files.write(file, lines, StandardCharsets.UTF_8);
			Files.write(file2, lines, StandardCharsets.UTF_8);
			Files.write(file3, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void deleteImage() {
		// Arrange & Act
		boolean deleteImage = ImageUtil.deleteFile(new File(STATIC_FOLDER + "test-file.jpg"));
		// Assert
		assertTrue(deleteImage);
	}

	@Test
	void deleteNonExistingImage() {
		// Arrange & Act
		boolean deletedImage = ImageUtil.deleteFile(new File(STATIC_FOLDER + "test-file.jpg"));
		// Assert
		assertFalse(deletedImage);
	}

	@Test
	void deleteImageFromName() {
		boolean deletedImage = ImageUtil.deleteImageFromStaticByName( "test-file2.jpg");
		assertTrue(deletedImage);
	}

	@Test
	void generateNewImageFromMultipart() {
		/*Path path = Paths.get(defaultFileDestination);
		String name = "image.jpg";
		String originalFileName = "image.jpg";
		String contentType = "image/jpg";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile image = new MockMultipartFile(name,
				originalFileName, contentType, content);*/

	}

	@Test
	void getExtensionFromFile() {
		String extension = ImageUtil.getExtensionFromFile("my-image.jpg");
		assertEquals("jpg", extension);
	}

	@Test
	void getExtensionFromEmptyFile() {
		String extension = ImageUtil.getExtensionFromFile("my-image");
		assertEquals("my-image", "my-image");
	}
}