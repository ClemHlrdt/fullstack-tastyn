package com.clemhlrdt.recipeapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecipeTest implements ModelTest {

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

		user = new User("clem", "clement.helardot@gmail.com", "password");
	}

	@Test
	void getId() {
		assertEquals(1, recipe.getId(), "Wrong user id");
	}

	@Test
	void getName() {
		assertEquals("Caesar Salad", recipe.getName());
	}

	@Test
	void getDuration() {
		assertEquals(20, recipe.getDuration());
	}

	@Test
	void getPreparation() {
		assertEquals("Caesar Salad prep", recipe.getPreparation());
	}

	@Test
	void getVotes() {
		assertEquals(0, recipe.getVotes());
	}

	@Test
	void getImagePath() {
		assertEquals("https://images.unsplash.com/photo-1588013273468-315fd88ea34c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80", recipe.getImagePath());
	}

	@Test
	void getDifficulty() {
		assertEquals(2, recipe.getDifficulty());
	}

	@Test
	void getServing() {
		assertEquals(2, recipe.getServing());
	}

	@Test
	void getPricing() {
		assertEquals(2, recipe.getPricing());
	}

	@Test
	void getIngredients() {
		assertEquals(2, recipe.getIngredients().size());
		assertTrue(recipe.getIngredients().contains(salad));
	}

	@Test
	void getUser() {
	}

	@Test
	void getComments() {
		assertEquals(0, recipe.getComments().size());
	}

	@Test
	void getRecipeVotes() {
		assertEquals(0, recipe.getVotes());
	}

	@Test
	void setId() {
		recipe.setId(2);
		assertEquals(2, recipe.getId());
	}

	@Test
	void setName() {
		recipe.setName("Chicken Salad");
		assertEquals("Chicken Salad", recipe.getName());
	}

	@Test
	void setDuration() {
		recipe.setDuration(25);
		assertEquals(25, recipe.getDuration());
	}

	@Test
	void setPreparation() {
		recipe.setPreparation("new preparation");
		assertEquals("new preparation", recipe.getPreparation());
	}

	@Test
	void setVotes() {
		recipe.setVotes(1);
		assertEquals(1, recipe.getVotes());
	}

	@Test
	void setImagePath() {
		recipe.setImagePath("https://images.unsplash.com/photo-1512058564366-18510be2db19?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1600&q=80");
		assertEquals("https://images.unsplash.com/photo-1512058564366-18510be2db19?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1600&q=80", recipe.getImagePath());
	}

	@Test
	void setDifficulty() {
		recipe.setDifficulty(1);
		assertEquals(1, recipe.getDifficulty());
	}

	@Test
	void setServing() {
		recipe.setServing(1);
		assertEquals(1, recipe.getServing());
	}

	@Test
	void setPricing() {
		recipe.setPricing(1);
		assertEquals(1, recipe.getPricing());
	}

	@Test
	void setIngredients() {
		Ingredient chicken = new Ingredient("Chicken Breast", "200G");
		ingredients.add(chicken);
		recipe.setIngredients(ingredients);
		assertEquals(recipe.getIngredients().size(), 3);
		assertTrue(recipe.getIngredients().contains(chicken));
	}

	@Test
	void setUser() {
		User user = new User("clem", "clement.helardot@gmail.com", "password");
		recipe.setUser(user);
		assertNotNull(recipe.getUser());
		assertEquals("clem",recipe.getUser().getUsername());
	}

	@Test
	void setComments() {
		Comment comment = new Comment(1, "Delicious", new User("clem", "clement.helardot@gmail.com", "password"), recipe);
		List<Comment> comments = new ArrayList<>();
		comments.add(comment);
		recipe.setComments(comments);

		assertEquals(1, recipe.getComments().size());
		assertEquals(comments, recipe.getComments());
	}

	@Test
	void setRecipeVotes() {
		List<RecipeVote> recipeVotes = new ArrayList<>();
		RecipeVote rv = new RecipeVote(1L, 1, recipe, user);
		recipeVotes.add(rv);
		recipe.setRecipeVotes(recipeVotes);

		assertEquals(1, recipe.getRecipeVotes().size());
		assertTrue(recipe.getRecipeVotes().contains(rv));
	}

	@Test
	void addIngredient() {
		recipe.addIngredient("Yogurt", "100g");
		assertThat(recipe.getIngredients().size()).isEqualTo(3);
		assertThat(recipe.getIngredients()).contains(salad);
	}

	@Test
	void addComment() {

		comment = new Comment(1, "Delicious", user, recipe);

		recipe.addComment(comment);
		assertEquals(1, recipe.getComments().size());
	}

	@Test
	void addVote() {
		RecipeVote vote = new RecipeVote();
		vote.setVoteId(1L);
		vote.setRecipe(recipe);
		vote.setUser(user);

		assertEquals(1, recipe.getRecipeVotes().size());
	}
}