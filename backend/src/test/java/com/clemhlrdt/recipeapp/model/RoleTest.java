package com.clemhlrdt.recipeapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest implements ModelTest {

	Role role;

	@BeforeEach
	void setUp() {
		role = new Role(RoleName.ROLE_USER);
		role.setId(1L);
	}

	@Test
	void getId() {
		assertEquals(1L, role.getId());
	}

	@Test
	void getName() {
		assertEquals(RoleName.ROLE_USER, role.getName());
	}

	@Test
	void setId() {
		role.setId(2L);
		assertEquals(2L, role.getId());
	}

	@Test
	void setName() {
		role.setName(RoleName.ROLE_ADMIN);
		assertEquals(RoleName.ROLE_ADMIN, role.getName());
	}
}