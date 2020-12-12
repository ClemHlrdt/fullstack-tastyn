package com.clemhlrdt.recipeapp.repository;


import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.model.RecipeVote;
import com.clemhlrdt.recipeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<RecipeVote, Long> {
	Optional<RecipeVote> findVoteByRecipeAndUser(Recipe recipe, User currentUser);
	Optional<RecipeVote> deleteVoteByRecipeAndUser(Recipe recipe, User currentUser);

	@Query(value = "SELECT sum(r.value) FROM RecipeVote r WHERE recipe.id=:recipeId")
	Optional<Integer> sumVotesByRecipe(@Param("recipeId") int recipeId);

	Optional<RecipeVote> findTopByRecipeAndUserOrderByVoteIdDesc(Recipe recipe, User currentUser);
}

