package com.clemhlrdt.recipeapp.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
class ImageCreatorImplTest {

	private static final String STATIC_FOLDER = System.getProperty("user.dir") + "/static/avatars/";

	ImageCreator imageCreator;


	@BeforeEach
	void setUp() {
		System.out.println("setup");
		imageCreator = new ImageCreator(STATIC_FOLDER);
	}

	@Test
	void addFile() {
		assertTrue(imageCreator.addFile("test", "txt"));
		assertFalse(imageCreator.addFile("test", "txt"));
	}

	@Test
	void renameFile() {
		imageCreator.addFile("test", "txt");
		assertTrue(imageCreator.renameFile("test.txt", "test_modified.txt"));
		assertFalse(imageCreator.renameFile("noexistingfile.txt", "noexistingfile_updated.txt"));
	}

	@Test
	void deleteFile() {
		assertTrue(imageCreator.addFile("test2", "txt"));
		assertTrue(imageCreator.deleteFile("test2.txt"));
		assertFalse(imageCreator.deleteFile("test2.txt"));
	}

	@Test
	void exists() {
		assertTrue(imageCreator.addFile("test3", "txt"));
		assertTrue(imageCreator.exists("test3.txt"));
	}
}