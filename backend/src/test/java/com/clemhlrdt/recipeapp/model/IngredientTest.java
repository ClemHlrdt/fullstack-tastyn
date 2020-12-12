package com.clemhlrdt.recipeapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest implements ModelTest {

	Ingredient ingredient;

	@BeforeEach
	void setUp() {
		ingredient = new Ingredient("Salad","1");
		ingredient.setId(1);
	}

	@Test
	void getId() {
		assertEquals(1 ,ingredient.getId());
	}

	@Test
	void getName() {
		assertEquals("Salad", ingredient.getName());
	}

	@Test
	void getAmount() {
		assertEquals("1", ingredient.getAmount());
	}

	@Test
	void setId() {
		ingredient.setId(2);
		assertEquals(2, ingredient.getId());
	}

	@Test
	void setName() {
		ingredient.setName("salad");
		assertEquals("salad", ingredient.getName());
	}

	@Test
	void setAmount() {
		ingredient.setAmount("2");
		assertEquals("2", ingredient.getAmount());
	}
}