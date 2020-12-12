package com.clemhlrdt.recipeapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest implements ModelTest {

	User user;
	Role userRole;
	Set<Role> roles;

	@BeforeEach
	void setUp() {
		userRole = new Role(RoleName.ROLE_USER);
		roles = new HashSet<>();
		roles.add(userRole);
		user = new User("clem", "clement.helardot@gmail.com", "password");
		user.setId(UUID.fromString("596be83b-8eff-4a38-b980-731e0fcffe01"));
		user.setRoles(roles);
	}

	@Test
	void getId() {
		assertEquals(UUID.fromString("596be83b-8eff-4a38-b980-731e0fcffe01"),user.getId(), "UUID is invalid");
	}

	@Test
	void getUsername() {
		assertEquals("clem", user.getUsername(), "Wrong username");
	}

	@Test
	void getEmail() {
		assertEquals("clement.helardot@gmail.com",user.getEmail(), "Wrong email");
	}

	@Test
	void getPassword() {
		assertEquals("password", user.getPassword(), "Wrong password");
	}

	@Test
	void getDescription() {
		assertNull(user.getDescription(), "Description is not null");
	}

	@Test
	void getAvatar() {
		assertNull(user.getAvatar(), "Avatar is not null");
	}

	@Test
	void getRecipes() {
		assertNull(user.getRecipes(), "Recipes aren't null");
	}

	@Test
	void getRoles() {
		assertEquals(roles ,user.getRoles(), "User doesn't have the role USER");
		assertFalse(user.getRoles().contains(RoleName.ROLE_ADMIN), "User has role ADMIN");
	}

	@Test
	void setId() {
		user.setId(UUID.fromString("58b56f8c-3fca-4252-adec-df95fca7a0a9"));
		assertEquals(UUID.fromString("58b56f8c-3fca-4252-adec-df95fca7a0a9"), user.getId());
	}

	@Test
	void setUsername() {
		user.setUsername("dan");
		assertEquals("dan", user.getUsername());
	}

	@Test
	void setEmail() {
		user.setEmail("dan@gmail.com");
		assertEquals("dan@gmail.com", user.getEmail());
	}

	@Test
	void setPassword() {
		user.setPassword("newPassword");
		assertEquals("newPassword", user.getPassword());
	}

	@Test
	void setDescription() {
		user.setDescription("Hello I am Dan from Vietnam");
		assertEquals("Hello I am Dan from Vietnam", user.getDescription());
	}

	@Test
	void setAvatar() {
		user.setAvatar("/avatars/cd3d3e7a-b450-4a10-bbfa-c33e20ef0ac0.jpg");
		assertEquals("/avatars/cd3d3e7a-b450-4a10-bbfa-c33e20ef0ac0.jpg", user.getAvatar());
	}

	@Test
	void setRecipes() {

		List<Recipe> recipes = new ArrayList<>();

		Ingredient salad = new Ingredient("Salad", "1");
		Ingredient tomato = new Ingredient("Tomato", "2");
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(salad);
		ingredients.add(tomato);

		Recipe recipe = new Recipe();
		recipe.setName("Caesar Salad");
		recipe.setDuration(20);
		recipe.setPreparation("Caesar Salad prep");
		recipe.setDifficulty(2);
		recipe.setServing(2);
		recipe.setPricing(2);
		recipe.setIngredients(ingredients);
		recipes.add(recipe);

		user.setRecipes(recipes);

		assertEquals(1, user.getRecipes().size());
		assertTrue(user.getRecipes().contains(recipe));
	}

	@Test
	void setRoles() {
		Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
		roles.add(roleAdmin);
		assertTrue(roles.contains(roleAdmin));
	}
}