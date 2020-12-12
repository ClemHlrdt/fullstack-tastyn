package com.clemhlrdt.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRecipeDto {
	private long totalRecipes;
	private long totalPages;

	private List<RecipeDto> recipes;
}
