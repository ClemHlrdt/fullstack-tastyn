package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.CommentDto;
import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.model.Comment;
import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CommentMapperTest implements MapperTest {

	CommentMapperImpl sut;

	@BeforeEach
	void setUp() {
		this.sut = new CommentMapperImpl();
	}

	@Test
	@DisplayName(value = "Map Comment to CommentDto")
	void mapCommentToDto() {
		Instant instant = Instant.now();
		// given
		Comment comment = new Comment();
		comment.setId(1);
		comment.setComment("Awesome");
		comment.setCreatedAt(instant);
		comment.setUpdatedAt(instant);
		comment.setUser(new User());
		comment.setRecipe(new Recipe());

		// when
		CommentDto commentDto= sut.mapCommentToDto(comment);

		// then
		assertThat(commentDto)
				.hasFieldOrPropertyWithValue("id", 1)
				.hasFieldOrPropertyWithValue("comment", "Awesome")
				.hasFieldOrProperty("user");
	}

	@Test
	@DisplayName(value = "Map CommentDto to Comment")
	void mapCommentDtoToComment() {
		// given
		CommentDto commentDto = new CommentDto(2, "Amazing recipe", new UserDto());

		// when
		Comment comment = sut.mapCommentDtoToComment(commentDto);

		// then
		assertThat(commentDto)
				.hasFieldOrPropertyWithValue("id", 2)
				.hasFieldOrPropertyWithValue("comment", "Amazing recipe")
				.hasFieldOrProperty("user");
	}
}