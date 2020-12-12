package com.clemhlrdt.recipeapp.payload;

import lombok.Data;

/*
	Class for which instances return the type of token & the accessToken
 */
@Data
public class JwtAuthenticationResponse {
	private String accessToken;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}