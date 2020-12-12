package com.clemhlrdt.recipeapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidFieldException extends ApiException {
	private List<ErrorModel> details;

	public InvalidFieldException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, List<ErrorModel> details) {
		super(message, httpStatus, timestamp);
		this.details = details;
	}
}
