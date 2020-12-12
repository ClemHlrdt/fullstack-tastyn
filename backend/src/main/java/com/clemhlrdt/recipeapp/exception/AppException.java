package com.clemhlrdt.recipeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException{
	public AppException(String message) {
		super(message);
		System.out.println("AppException");
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
		System.out.println("AppException");
	}
}
