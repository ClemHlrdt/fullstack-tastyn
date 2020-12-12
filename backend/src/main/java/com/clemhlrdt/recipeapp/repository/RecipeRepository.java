package com.clemhlrdt.recipeapp.repository;

import com.clemhlrdt.recipeapp.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	Page<Recipe> findByNameContainingIgnoreCase(String recipe, Pageable pageable);

	Page<Recipe> findByUserIdAndNameContainingIgnoreCase(UUID userId, String name, Pageable pageable);

	@Query("FROM Recipe r WHERE r.user.id = :userId")
	Page<Recipe> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);

	@Query("FROM Recipe r WHERE r.user.username = :username")
	Page<Recipe> findAllByUsername(@Param("username") String username, Pageable pageable);
}
