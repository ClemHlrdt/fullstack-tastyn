package com.clemhlrdt.recipeapp.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {
	private int id;
	private String name;
	private int duration;
	private int votes;
	private String preparation;
	private int difficulty;
	private String imagePath;
	private int serving;
	private int pricing;
	private Instant createdAt;

	private List<IngredientDto> ingredients;
	private UserDto user;
	private List<CommentDto> comments;
}
