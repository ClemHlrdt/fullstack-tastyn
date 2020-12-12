package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.RecipeDto;
import com.clemhlrdt.recipeapp.model.Ingredient;
import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeMapperTest implements MapperTest{

	private RecipeMapperImpl sut;

	@BeforeEach
	void setUp() {
		this.sut = new RecipeMapperImpl();
	}

	@Test
	@DisplayName("Map a null recipe to RecipeDto")
	void mapNullRecipeToDto() {
		// given

		// when
		RecipeDto recipeDto = sut.mapRecipeToDto(null);

		// then
		assertThat(recipeDto).isNull();
	}

	@Test
	void mapNullRecipeDtoToRecipe() {
		// given

		// when
		Recipe recipe = sut.mapRecipeDtoToRecipe(null);

		// then
		assertThat(recipe).isNull();
	}

	@Test
	void updateNullRecipeDtoToRecipe() {
		// given
		Recipe recipe = new Recipe();

		// when
		sut.updateRecipeFromDto(null, recipe);

		// then
		assertThat(recipe).isEqualTo(recipe);
	}

	@Test
	@DisplayName(value = "Map Recipe to RecipeDto")
	void mapRecipeToDto() {
		// given
		Recipe recipe = new Recipe();
		Instant instant = Instant.now();

		recipe.setId(1);
		recipe.setName("Spaghetti Carbonara");
		recipe.setDuration(10);
		recipe.setPreparation("lorem ipsum");
		recipe.setVotes(0);
		recipe.setImagePath("https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80");
		recipe.setDifficulty(1);
		recipe.setPricing(1);
		Ingredient ing1 = new Ingredient("Spaghetti", "100g");
		Ingredient ing2 = new Ingredient("Lardon", "30g");
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(ing1);
		ingredients.add(ing2);
		recipe.setIngredients(ingredients);
		recipe.setUser(new User("clem", "clement.helardot@gmail", "password"));

		// when
		RecipeDto recipeDto = sut.mapRecipeToDto(recipe);

		// then
		assertThat(recipeDto)
				.hasFieldOrPropertyWithValue("id", 1)
				.hasFieldOrPropertyWithValue("name", "Spaghetti Carbonara")
				.hasFieldOrPropertyWithValue("duration", 10)
				.hasFieldOrPropertyWithValue("preparation", "lorem ipsum")
				.hasFieldOrPropertyWithValue("votes", 0)
				.hasFieldOrPropertyWithValue("imagePath", "https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80")
				.hasFieldOrPropertyWithValue("difficulty", 1)
				.hasFieldOrPropertyWithValue("pricing", 1)
				.hasFieldOrProperty("ingredients")
				.hasFieldOrProperty("user");
	}

	@Test
	void mapRecipeDtoToRecipe() {
		// given
		RecipeDto recipeDto = new RecipeDto();
		recipeDto.setId(1);
		recipeDto.setName("Spaghetti Carbonara");
		recipeDto.setDuration(10);
		recipeDto.setPreparation("lorem ipsum");
		recipeDto.setVotes(0);
		recipeDto.setImagePath("https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80");
		recipeDto.setDifficulty(1);
		recipeDto.setPricing(1);

		// when
		Recipe recipe = sut.mapRecipeDtoToRecipe(recipeDto);

		// then
		assertThat(recipe)
				.hasFieldOrPropertyWithValue("id", 1)
				.hasFieldOrPropertyWithValue("name", "Spaghetti Carbonara")
				.hasFieldOrPropertyWithValue("duration", 10)
				.hasFieldOrPropertyWithValue("preparation", "lorem ipsum")
				.hasFieldOrPropertyWithValue("votes", 0)
				.hasFieldOrPropertyWithValue("imagePath", "https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80")
				.hasFieldOrPropertyWithValue("difficulty", 1)
				.hasFieldOrPropertyWithValue("pricing", 1)
				.hasFieldOrProperty("ingredients")
				.hasFieldOrProperty("user");
	}

	@Test
	void updateRecipeFromDto() {
		// given
		RecipeDto recipeDto = new RecipeDto();
		recipeDto.setId(1);
		recipeDto.setName("Spaghetti Carbonara");
		recipeDto.setDuration(10);
		recipeDto.setPreparation("lorem ipsum updated");
		recipeDto.setVotes(0);
		recipeDto.setImagePath("https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80");
		recipeDto.setDifficulty(1);
		recipeDto.setPricing(1);

		Recipe recipe = new Recipe();
		Instant instant = Instant.now();

		recipe.setId(1);
		recipe.setName("Spaghetti Carbonara");
		recipe.setDuration(10);
		recipe.setPreparation("lorem ipsum");
		recipe.setVotes(0);
		recipe.setImagePath("https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80");
		recipe.setDifficulty(1);
		recipe.setPricing(1);
		Ingredient ing1 = new Ingredient("Spaghetti", "100g");
		Ingredient ing2 = new Ingredient("Lardon", "30g");
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(ing1);
		ingredients.add(ing2);
		recipe.setIngredients(ingredients);
		recipe.setUser(new User("clem", "clement.helardot@gmail", "password"));

		// then
		sut.updateRecipeFromDto(recipeDto, recipe);

		// when
		assertThat(recipe)
				.hasFieldOrPropertyWithValue("id", 1)
				.hasFieldOrPropertyWithValue("name", "Spaghetti Carbonara")
				.hasFieldOrPropertyWithValue("duration", 10)
				.hasFieldOrPropertyWithValue("preparation", "lorem ipsum updated");
	}
}