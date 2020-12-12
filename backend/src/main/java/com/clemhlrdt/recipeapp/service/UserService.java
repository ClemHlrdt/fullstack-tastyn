package com.clemhlrdt.recipeapp.service;


import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.exception.ResourceNotFoundException;
import com.clemhlrdt.recipeapp.exception.UnauthorizedException;
import com.clemhlrdt.recipeapp.mapper.UserMapper;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import com.clemhlrdt.recipeapp.utils.AvatarCreator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

	UserRepository userRepository;

	UserMapper userMapper;

	AvatarCreator avatarCreator;

	public UserService(UserRepository userRepository, UserMapper userMapper, AvatarCreator avatarCreator) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.avatarCreator = avatarCreator;
	}

	public List<UserDto> getAllUsers(int page, int size, String sortBy){

		Pageable paging = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);

		return userRepository.findAll(paging).map(userMapper::mapUserToDto).toList();
	}

	public UserDto getUserById(String userId) {
		User user = userRepository.findById(UUID.fromString(userId))
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return userMapper.mapUserToDto(user);
	}

	public UserDto updateUserById(String userId, UserDto userDto, UserPrincipal userPrincipal){

		UUID id = UUID.fromString(userId);

		// Check if it's the same user or raise an exception
		if(!userPrincipal.getId().equals(id)){
			throw new UnauthorizedException("User isn't the same");
		}

		// Get user
		User userFound = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		userMapper.updateUserFromDto(userDto, userFound);
		userRepository.save(userFound);

		return userMapper.mapUserToDto(userFound);
	}

	public UserDto setUserAvatar(String userId, MultipartFile file) {

			// check if user has an avatar already
			if(this.getUserById(userId).getAvatar() != null){
				this.avatarCreator.deleteFile(userId);
			}

			String path = "avatars/" + userId;

			String fileName = avatarCreator.createAvatar(file, 600, userId);

			// Get user by name, set the avatar and save
			User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
			user.setAvatar("/avatars/" + fileName);

			User save = userRepository.save(user);

			return userMapper.mapUserToDto(save);

	}

	public void deleteUserById(String userId, UserPrincipal userPrincipal){

		User userFound = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

		if(userFound.getId().equals(userPrincipal.getId())){
			userRepository.delete(userFound);
		} else {
			throw new UnauthorizedException();
		}
	}





}
