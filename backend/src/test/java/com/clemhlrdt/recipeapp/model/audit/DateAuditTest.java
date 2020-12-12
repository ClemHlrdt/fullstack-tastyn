package com.clemhlrdt.recipeapp.model.audit;

import com.clemhlrdt.recipeapp.model.ModelTest;
import com.clemhlrdt.recipeapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateAuditTest implements ModelTest {

	User user;

	@BeforeEach
	void setUp() {
		user = new User("clem", "clement.helardot@gmail.com", "password");

	}

	@Test
	void getCreatedTest() {
		Instant date = Instant.now();

		user.setCreatedAt(date);
		assertEquals(date, user.getCreatedAt());
	}

	@Test
	void getUpdatedAtTest() {
		Instant date = Instant.now();
		user.setUpdatedAt(date);

		assertEquals(date, user.getUpdatedAt());
	}
}