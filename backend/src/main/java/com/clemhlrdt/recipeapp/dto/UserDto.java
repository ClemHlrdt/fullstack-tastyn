package com.clemhlrdt.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private UUID id;
	private String username;
	private String description;
	private String email;
	private String avatar;
	private Instant createdAt;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDto userDto = (UserDto) o;
		return Objects.equals(id, userDto.id) &&
				username.equals(userDto.username) &&
				Objects.equals(description, userDto.description) &&
				email.equals(userDto.email) &&
				Objects.equals(avatar, userDto.avatar) &&
				Objects.equals(createdAt, userDto.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, description, email, avatar, createdAt);
	}
}
