package com.clemhlrdt.recipeapp.utils;


import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

	private static final String STATIC_FOLDER = System.getProperty("user.dir") + "/static/";

	public static boolean deleteFile(File file){
		return file.delete();
	}

	public static boolean deleteImageFromStaticByName(String fileName){
		String fullName = STATIC_FOLDER + fileName;
		File fileToDelete = new File(fullName);

		return deleteFile(fileToDelete);
	}

	public static String generateNewImageFromMultipart(MultipartFile file, int size, String path){

		String newFileName = "";
		// get file name
		String avatar = file.getOriginalFilename();

		try {
			assert avatar != null;
			newFileName = path + "." + getExtensionFromFile(avatar);
			String fullPath = STATIC_FOLDER + newFileName;

			file.transferTo(new File(fullPath));

			BufferedImage image = ImageIO.read(new File(fullPath) );

			image = simpleResizeImage(image, size);
			ImageIO.write(image, getExtensionFromFile(fullPath), new File(fullPath));
		} catch (IOException e){
			System.out.println("Cannot generate a new image !");
			e.printStackTrace();
		}

		return newFileName;
	}

	public static String getExtensionFromFile(String filename) {

		// get dot index
		int index = (filename == null ) ? Integer.parseInt("") : filename.lastIndexOf('.');
		String ext = "";
		try {
			assert filename != null;
			ext = filename.substring(index + 1);
		} catch (Exception e){
			System.out.println("The file doesn't have an extension");
			e.printStackTrace();
		}
		return ext;
	}

	static BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) {
		try {
			return Scalr.resize(originalImage, targetWidth);
		} catch (RuntimeException e){
			System.out.println("Cannot resize the image !");
			e.printStackTrace();
		}

		return originalImage;
	}
}
