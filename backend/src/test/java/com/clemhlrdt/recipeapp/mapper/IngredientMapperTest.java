package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.IngredientDto;
import com.clemhlrdt.recipeapp.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class IngredientMapperTest implements MapperTest{

	private IngredientMapperImpl sut;

	@BeforeEach
	void setUp() {
		this.sut = new IngredientMapperImpl();
	}

	@Test
	void mapIngredientToDto() {
		// given
		Ingredient ing = new Ingredient(1, "Salad", "1");

		// when
		IngredientDto ingredientDto = sut.mapIngredientToDto(ing);

		// then
		assertThat(ingredientDto)
				.hasFieldOrPropertyWithValue("name", "Salad")
				.hasFieldOrPropertyWithValue("amount", "1");
	}

	@Test
	void mapIngredientDtoToIngredient() {
		// given
		IngredientDto ingredientDto = new IngredientDto("Salad", "1");
		// when
		Ingredient ingredient = sut.mapIngredientDtoToIngredient(ingredientDto);
		// then
		assertThat(ingredient)
				.hasFieldOrPropertyWithValue("name", "Salad")
				.hasFieldOrPropertyWithValue("amount", "1");
	}
}