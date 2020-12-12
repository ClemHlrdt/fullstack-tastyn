package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.model.Role;
import com.clemhlrdt.recipeapp.model.RoleName;
import com.clemhlrdt.recipeapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest implements MapperTest{

	private UserMapperImpl sut;

	@BeforeEach
	void setUp() {
		sut = new UserMapperImpl();
	}

	@Test
	void mapUserToDto() {
		// given
		User user = new User();
		Instant instant = Instant.now();
		user.setId(UUID.fromString("be260f62-3775-4e14-bfa8-309edf6c46bd"));
		user.setCreatedAt(instant);
		user.setUsername("clem");
		user.setEmail("clement.helardot@gmail.com");
		user.setPassword("password");
		user.setDescription("custom description");
		user.setAvatar("/avatars/cd3d3e7a-b450-4a10-bbfa-c33e20ef0ac0.png");
		user.setRoles(Collections.singleton(new Role(RoleName.ROLE_USER)));


		// when
		UserDto userDto = sut.mapUserToDto(user);


		// then
		assertThat(userDto)
				.hasFieldOrPropertyWithValue("id", UUID.fromString("be260f62-3775-4e14-bfa8-309edf6c46bd"))
				.hasFieldOrPropertyWithValue("username", "clem")
				.hasFieldOrPropertyWithValue("description", "custom description")
				.hasFieldOrPropertyWithValue("email", "clement.helardot@gmail.com")
				.hasFieldOrPropertyWithValue("avatar", "/avatars/cd3d3e7a-b450-4a10-bbfa-c33e20ef0ac0.png")
				.hasFieldOrPropertyWithValue("createdAt", instant);
	}

	@Test
	void mapUserDtoToUser() {
		// given
		UserDto userDto = new UserDto();
		userDto.setUsername("clem");
		userDto.setEmail("clement.helardot@gmail.com");
		userDto.setDescription("custom description");

		// when
		User user = sut.mapUserDtoToUser(userDto);

		// then
		assertThat(userDto)
				.hasFieldOrPropertyWithValue("username", "clem")
				.hasFieldOrPropertyWithValue("email", "clement.helardot@gmail.com")
				.hasFieldOrPropertyWithValue("description", "custom description");
	}

	@Test
	void updateUserFromDto() {
		// given
		UserDto userDto = new UserDto();
		userDto.setUsername("clement");
		userDto.setEmail("clement.helardot@gmail.com");
		userDto.setDescription("custom description");

		User user = new User();
		Instant instant = Instant.now();
		user.setId(UUID.fromString("be260f62-3775-4e14-bfa8-309edf6c46bd"));
		user.setCreatedAt(instant);
		user.setUsername("clem");
		user.setEmail("clement.helardot@gmail.com");
		user.setPassword("password");
		user.setDescription("custom description");
		user.setAvatar("/avatars/cd3d3e7a-b450-4a10-bbfa-c33e20ef0ac0.png");
		user.setRoles(Collections.singleton(new Role(RoleName.ROLE_USER)));

		// when
		sut.updateUserFromDto(userDto, user);

		// then
		assertThat(user)
				.hasFieldOrPropertyWithValue("username", "clement")
				.hasFieldOrPropertyWithValue("email", "clement.helardot@gmail.com")
				.hasFieldOrPropertyWithValue("description", "custom description");
	}
}