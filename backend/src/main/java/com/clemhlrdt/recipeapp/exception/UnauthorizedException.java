package com.clemhlrdt.recipeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "The user does not have authorization to perform this action.")
public class UnauthorizedException extends RuntimeException{
	public UnauthorizedException() {
	}

	public UnauthorizedException(String message) {
		super(message);
	}
}
