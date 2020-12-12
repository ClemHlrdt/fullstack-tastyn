package com.clemhlrdt.recipeapp.repository;

import com.clemhlrdt.recipeapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Query("FROM Comment c WHERE c.recipe.id = :recipeId")
	List<Comment> getAllCommentForRecipeId(@Param("recipeId") Integer recipeId);
}
