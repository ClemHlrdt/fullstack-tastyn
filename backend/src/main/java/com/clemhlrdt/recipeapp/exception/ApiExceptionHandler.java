package com.clemhlrdt.recipeapp.exception;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
		// 1. Create a payload to send through the Response Entity (exception + detail)
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		ApiException apiException = new ApiException(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		// 2. Return the Response Entity
		return new ResponseEntity<>(apiException, badRequest);
	}


	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handleApiRequestException(MethodArgumentNotValidException e){
		// 1. Create a payload to send through the Response Entity (exception + detail)
		List<ErrorModel> errorMessages = e.getBindingResult().getFieldErrors().stream()
				.map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
				.distinct()
				.collect(Collectors.toList());
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		InvalidFieldException invalidFieldException = new InvalidFieldException(
				"INVALID_FORM",
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z")),
				errorMessages
		);

		// 2. Return the Response Entity
		return new ResponseEntity<>(invalidFieldException, badRequest);
	}

	@ExceptionHandler(value = {BadCredentialsException.class, SignatureException.class})
	public ResponseEntity<Object> handleBadCredentials(BadCredentialsException e){
		// 1. Create a payload to send through the Response Entity (exception + detail)
		HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
		ApiException apiException = new ApiException(
				"UNAUTHORIZED",
				unauthorized,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		// 2. Return the Response Entity
		return new ResponseEntity<>(apiException, unauthorized);
	}

	@ExceptionHandler(value = {FileWithoutExtensionException.class})
	public ResponseEntity<Object> handleFileWithoutExtension(FileWithoutExtensionException e){
		// 1. Create a payload to send through the Response Entity (exception + detail)
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		ApiException apiException = new ApiException(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		// 2. Return the Response Entity
		return new ResponseEntity<>(apiException, badRequest);
	}
}
