package com.clemhlrdt.recipeapp.utils;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface IAvatarCreator {
	String createAvatar(MultipartFile file, int width, String path);
	Optional<String> getFileExtension(String filename);
	BufferedImage resizeBufferedImage(BufferedImage original, int width);
	boolean deleteFile(String userId);
}
