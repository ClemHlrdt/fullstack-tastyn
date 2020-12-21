package com.clemhlrdt.recipeapp.service;

import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.exception.ApiRequestException;
import com.clemhlrdt.recipeapp.exception.AppException;
import com.clemhlrdt.recipeapp.exception.ResourceNotFoundException;
import com.clemhlrdt.recipeapp.exception.UnauthorizedException;
import com.clemhlrdt.recipeapp.mapper.UserMapper;
import com.clemhlrdt.recipeapp.model.Role;
import com.clemhlrdt.recipeapp.model.RoleName;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.payload.JwtAuthenticationResponse;
import com.clemhlrdt.recipeapp.payload.LoginRequest;
import com.clemhlrdt.recipeapp.payload.PasswordUpdateRequest;
import com.clemhlrdt.recipeapp.payload.SignUpRequest;
import com.clemhlrdt.recipeapp.repository.RoleRepository;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import com.clemhlrdt.recipeapp.security.CustomUserDetailsService;
import com.clemhlrdt.recipeapp.security.JwtUtil;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class AuthenticationService {

	@Value("${app.expirationTime}")
	private int expirationTime;

	AuthenticationManager authenticationManager;

	UserRepository userRepository;

	RoleRepository roleRepository;

	PasswordEncoder passwordEncoder;

	CustomUserDetailsService customUserDetailsService;

	JwtUtil tokenProvider;

	UserMapper userMapper;

	public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, JwtUtil tokenProvider, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.customUserDetailsService = customUserDetailsService;
		this.tokenProvider = tokenProvider;
		this.userMapper = userMapper;
	}

	public JwtAuthenticationResponse loginUser(LoginRequest loginRequest) {

		// Attempts to authenticate a user & return fully populated Authentication object if successful
		try {
			authenticationManager.authenticate (
					new UsernamePasswordAuthenticationToken(
							loginRequest.getEmail(),
							loginRequest.getPassword()
					)
			);
		} catch (BadCredentialsException e){
			throw new ApiRequestException("BAD_CREDENTIALS");
		}

		// Fetch user details by email
		UserPrincipal userDetails = (UserPrincipal) customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

		String jwt = tokenProvider.generateToken(userDetails, new Date());

		return new JwtAuthenticationResponse(jwt);
	}

	public User registerUser(SignUpRequest signUpRequest){

		// Check if username if taken
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new ApiRequestException("USERNAME_TAKEN");
		}
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new ApiRequestException("EMAIL_TAKEN");
		}

		// Creating user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(), signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not found."));

		user.setRoles(Collections.singleton(userRole));

		return userRepository.save(user);
	}

	public UserDto updateUserPassword(PasswordUpdateRequest passwordUpdateRequest){
		User userFound = userRepository.findByEmail(passwordUpdateRequest.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", passwordUpdateRequest.getEmail()));

		if(!passwordEncoder.matches(passwordUpdateRequest.getPassword(), userFound.getPassword())){
			throw new UnauthorizedException("The credentials are not valid");
		}

		userFound.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
		User user = userRepository.save(userFound);
		return userMapper.mapUserToDto(user);
	}
}
