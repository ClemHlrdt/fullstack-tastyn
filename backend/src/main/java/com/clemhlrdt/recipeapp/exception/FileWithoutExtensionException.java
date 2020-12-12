package com.clemhlrdt.recipeapp.exception;

public class FileWithoutExtensionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String msg;

	public FileWithoutExtensionException(String msg){
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return this.msg;
	}
}
