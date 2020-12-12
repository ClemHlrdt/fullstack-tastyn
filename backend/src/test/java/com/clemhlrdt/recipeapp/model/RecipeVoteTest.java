package com.clemhlrdt.recipeapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeVoteTest implements ModelTest {

	RecipeVote recipeVote;
	User user = new User("clem", "clement.helardot@gmail.com", "password");
	Recipe recipe;
	List<Ingredient> ingredients;
	Ingredient salad;
	Ingredient tomato;

	@BeforeEach
	void setUp() {

		recipe = new Recipe();
		salad = new Ingredient("Salad", "1");
		tomato = new Ingredient("Tomato", "2");
		ingredients = new ArrayList<>();
		ingredients.add(salad);
		ingredients.add(tomato);
		recipe.setId(1);
		recipe.setName("Caesar Salad");
		recipe.setDuration(20);
		recipe.setPreparation("Caesar Salad prep");
		recipe.setImagePath("https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80");
		recipe.setDifficulty(2);
		recipe.setServing(2);
		recipe.setPricing(2);
		recipe.setIngredients(ingredients);


		recipeVote = new RecipeVote();
		recipeVote.setUser(user);
		recipeVote.setRecipe(recipe);
		recipeVote.setValue(-1);
		recipeVote.setVoteId(1L);
	}

	@Test
	void recipeVoteTest() {
		assertAll("Create a recipeVote",
				() -> assertEquals(1L, recipeVote.getVoteId()),
				() -> assertEquals(recipe, recipeVote.getRecipe()),
				() -> assertEquals(user, recipeVote.getUser()),
				() -> assertEquals(-1, recipeVote.getValue())
		);
	}
}