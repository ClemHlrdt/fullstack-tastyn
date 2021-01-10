package com.clemhlrdt.recipeapp.controllers;

import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.payload.JwtAuthenticationResponse;
import com.clemhlrdt.recipeapp.payload.LoginRequest;
import com.clemhlrdt.recipeapp.payload.PasswordUpdateRequest;
import com.clemhlrdt.recipeapp.payload.SignUpRequest;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import com.clemhlrdt.recipeapp.service.AuthenticationService;
import com.clemhlrdt.recipeapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/auth")
public class AuthController {


	UserRepository userRepository;

	UserService userService;

	AuthenticationService authenticationService;

	public AuthController(UserRepository userRepository, UserService userService, AuthenticationService authenticationService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.loginUser(loginRequest);

		return ResponseEntity.ok(jwtAuthenticationResponse);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		User user = authenticationService.registerUser(signUpRequest);

		LoginRequest login = new LoginRequest();

		login.setEmail(signUpRequest.getEmail());
		login.setPassword(signUpRequest.getPassword());

		JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.loginUser(login);

		return ResponseEntity.ok(jwtAuthenticationResponse);
	}

	@PostMapping("/update-password")
	public ResponseEntity<UserDto> updateUserPassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
		return ResponseEntity
						.status(HttpStatus.OK)
						.body(authenticationService.updateUserPassword(passwordUpdateRequest));
	}

}