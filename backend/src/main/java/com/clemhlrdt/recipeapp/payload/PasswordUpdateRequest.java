package com.clemhlrdt.recipeapp.payload;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@ToString
public class PasswordUpdateRequest {

	@Size(min = 5, max = 40, message = "INVALID_EMAIL")
	@Email(message = "INVALID_EMAIL")
	private String email;

	@Size(min = 6, max = 20, message = "INVALID_PASSWORD")
	private String password;

	@Size(min = 6, max = 20, message = "INVALID_NEW_PASSWORD")
	private String newPassword;
}
