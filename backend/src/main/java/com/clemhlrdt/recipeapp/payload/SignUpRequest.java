package com.clemhlrdt.recipeapp.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

	//@NotBlank(message = "Username can't be blank")
	@Size(min = 3, max = 15, message = "INVALID_USERNAME")
	private String username;

	//@NotBlank(message = "Email can't be blank")
	@Size(min = 5, max = 40, message = "INVALID_EMAIL")
	@Email(message = "INVALID_EMAIL")
	private String email;

	//@NotBlank(message = "Password can't be blank")
	@Size(min = 6, max = 20, message = "INVALID_PASSWORD")
	private String password;
}

