package com.clemhlrdt.recipeapp.utils;

import com.clemhlrdt.recipeapp.exception.FileWithoutExtensionException;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public class AvatarCreator extends ImageCreator implements IAvatarCreator {

	private static final String STATIC_FOLDER = System.getProperty("user.dir") + "/static/avatars/";

	public AvatarCreator() {
		super(STATIC_FOLDER);
	}

	public String createAvatar(MultipartFile file, int width, String userId){
		Optional<String> extension = getFileExtension(file.getOriginalFilename());
		extension.orElseThrow(() -> new FileWithoutExtensionException("The file has no extension: " + file.getOriginalFilename()));

		String fileName = userId + "." + extension.get();
		String fullPath = STATIC_FOLDER + fileName;

		try {
			file.transferTo(new File(fullPath));

			// Resize
			BufferedImage image = ImageIO.read(new File(fullPath));
			image = resizeBufferedImage(image, width);
			ImageIO.write(image, extension.get(), new File(fullPath));

		} catch (IOException e){
			System.out.println("Cannot generate a new image !");
			e.printStackTrace();
		}

		return fileName;
	}

	public Optional<String> getFileExtension(String filename) {
		return Optional.ofNullable(filename)
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	public BufferedImage resizeBufferedImage(BufferedImage originalImage, int targetWidth){
		try {
			originalImage = Scalr.resize(originalImage, targetWidth);
		} catch (RuntimeException e){
			System.out.println("Cannot resize the image!");
			e.printStackTrace();
		}
		return originalImage;
	}

	@Override
	public boolean deleteFile(String filename) {
		return super.deleteFile(filename);
	}
}
