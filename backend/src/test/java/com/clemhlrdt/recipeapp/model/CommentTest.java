package com.clemhlrdt.recipeapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest implements ModelTest {

	Recipe recipe;
	List<Ingredient> ingredients;
	Ingredient salad;
	Ingredient tomato;

	User user;

	Comment comment;

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

		comment = new Comment();
		comment.setId(1);
		comment.setRecipe(recipe);
		comment.setComment("Wow");
		comment.setUser(user);

	}

	@Test
	void testComment() {
		assertAll("Create a new comment",
				() -> assertEquals(1, comment.getId()),
				() -> assertEquals("Wow", comment.getComment()),
				() -> assertEquals(recipe, comment.getRecipe()),
				() -> assertEquals(user, comment.getUser())
		);
	}
}