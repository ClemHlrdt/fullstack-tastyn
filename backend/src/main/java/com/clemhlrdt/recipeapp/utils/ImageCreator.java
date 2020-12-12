package com.clemhlrdt.recipeapp.utils;

import java.io.File;
import java.io.IOException;

public class ImageCreator implements IFileCreator {

	private final String folderPath;

	public ImageCreator(String folderPath) {
		this.folderPath = folderPath;
	}

	@Override
	public boolean addFile(String filename, String extension) {
		try {
			File image = new File(folderPath + filename + "." +extension);
			if(image.createNewFile()){
				System.out.println(filename + " created at " + folderPath);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean renameFile(String oldFileName, String newFilename) {
		if(this.exists(oldFileName)){
			File oldFile = new File(folderPath + oldFileName);
			File newFile = new File(folderPath + newFilename);
			return oldFile.renameTo(newFile);
		}
		return false;
	}

	@Override
	public boolean deleteFile(String filename) {
		return new File(folderPath + filename).delete();
	}

	@Override
	public boolean exists(String filename) {
		return new File(folderPath + filename).exists();
	}
}
