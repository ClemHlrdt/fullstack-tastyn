package com.clemhlrdt.recipeapp.utils;

import java.io.IOException;

public interface IFileCreator {
	boolean addFile(String filename, String extension) throws IOException;
	boolean renameFile(String oldFileName, String newFilename) throws IOException;
	//boolean updateFile(String filename, File file) throws IOException;
	boolean deleteFile(String filename);
	boolean exists(String filename);
}
