package com.clemhlrdt.recipeapp.service;

import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.exception.ResourceNotFoundException;
import com.clemhlrdt.recipeapp.exception.UnauthorizedException;
import com.clemhlrdt.recipeapp.mapper.UserMapper;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import com.clemhlrdt.recipeapp.utils.AvatarCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	UserMapper userMapper;

	@Mock
	AvatarCreator avatarCreator;

	@InjectMocks
	UserService service;

	Pageable pageable;

	int page;

	int size;

	String sortBy;

	List<User> users;
	User user;
	List<UserDto> usersDto;
	UserDto userDto;

	UUID userRandomUUID = UUID.randomUUID();

	Authentication auth;

	@BeforeEach
	void setUp() {
		page = 1;
		size = 20;
		sortBy = "id";
		pageable = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
		users = new ArrayList<>();
		user = new User("clem", "clement.helardot@gmail.com", "password");
		user.setId(userRandomUUID);
		users.add(user);

		userDto = new UserDto();
		userDto.setUsername("clem");
		userDto.setEmail("clement.helardot@gmail.com");
		usersDto = new ArrayList<>();
		usersDto.add(userDto);
	}

	@Test
	void getAllUsers() {
		// Given
		Page<User> userPage = new PageImpl<>(users, pageable, users.size());

		given(userRepository.findAll(pageable)).willReturn(userPage);
		given(userMapper.mapUserToDto(user)).willReturn(userDto);

		// When
		List<UserDto> listUserDto = service.getAllUsers(page, size, sortBy);

		// Then
		assertThat(listUserDto).isEqualTo(usersDto);
		assertThat(listUserDto).hasSize(1);
		assertThat(listUserDto.contains(userDto));
	}

	@Test
	void getUserById() {
		// given
		Optional<User> optionalUser = Optional.of(user);
		given(userRepository.findById(userRandomUUID)).willReturn(optionalUser);
		given(userMapper.mapUserToDto(optionalUser.get())).willReturn(userDto);

		// when
		UserDto resultUserDto = service.getUserById(userRandomUUID.toString());

		// then
		assertThat(resultUserDto).isEqualTo(userDto);
	}

	@Test
	void getUserById__shouldThrowException() {
		// given
		UUID randomUUID = UUID.randomUUID();
		doThrow(ResourceNotFoundException.class).when(userRepository).findById(randomUUID);
		Exception exception = null;

		// when
		try {
			userRepository.findById(randomUUID);
		} catch (ResourceNotFoundException e){
			exception = e;
		}

		// then
		assertNotNull(exception);

	}

	@Disabled
	@Test
	void updateUserById_wrongCredentials() {
		// given
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		UserDto userDto = new UserDto(UUID.randomUUID(), "john", null, "john@gmail.com", null, Instant.now());
		User userFound = (User) given(userRepository.findById(user.getId())).willReturn(Optional.ofNullable(user));


		// when
		when(service.updateUserById(userPrincipal.getId().toString(), userDto, userPrincipal)).thenThrow(new UnauthorizedException("User isn't the same"));

		// then

	}

	@Test
	void setUserAvatar() {
	}

	@Test
	void deleteUserById() {
	}
}