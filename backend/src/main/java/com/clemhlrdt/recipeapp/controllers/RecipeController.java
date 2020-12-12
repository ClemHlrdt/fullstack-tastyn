package com.clemhlrdt.recipeapp.controllers;

import com.clemhlrdt.recipeapp.dto.CommentDto;
import com.clemhlrdt.recipeapp.dto.PageRecipeDto;
import com.clemhlrdt.recipeapp.dto.RecipeDto;
import com.clemhlrdt.recipeapp.mapper.RecipeMapper;
import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import com.clemhlrdt.recipeapp.service.CommentService;
import com.clemhlrdt.recipeapp.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/recipes")
@AllArgsConstructor
@Slf4j
public class RecipeController {

	private final RecipeService recipeService;
	private final CommentService commentService;
	private final RecipeMapper recipeMapper;

	@PostMapping
	public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.save(recipeDto, userPrincipal));
	}

	@GetMapping
	public ResponseEntity<PageRecipeDto> getAllRecipes(@RequestParam(value = "query", defaultValue = "", required = false) String query,
													   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
													   @RequestParam(value = "order", defaultValue = "ASC" , required = false) String order,
													   @RequestParam(value = "sort", defaultValue = "id", required = false)  String sort ){

		Page<Recipe> resultPage;
		if(query.length() > 0){
			resultPage = recipeService.searchForRecipeByName(query, page, 20, order, sort);
		} else {
			resultPage = recipeService.getAll(page, 20, order, sort);

		}

		long totalRecipes = resultPage.getTotalElements();
		long totalPages = resultPage.getTotalPages();

		List<RecipeDto> listRecipes = resultPage.getContent().stream().map(recipeMapper::mapRecipeToDto)
				.collect(toList());

		PageRecipeDto pageRecipeDto = new PageRecipeDto();
		pageRecipeDto.setRecipes(listRecipes);
		pageRecipeDto.setTotalPages(totalPages);
		pageRecipeDto.setTotalRecipes(totalRecipes);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(pageRecipeDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecipeDto> getRecipe(@PathVariable int id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(recipeService.getRecipe(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<RecipeDto> updateRecipe(@PathVariable int id, @RequestBody RecipeDto recipeDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(recipeService.updateRecipe(id, recipeDto, userPrincipal));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable int id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
		recipeService.deleteRecipe(id, userPrincipal);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{recipeId}/comment")
	public ResponseEntity<CommentDto> createComment(@PathVariable Integer recipeId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(recipeId, userPrincipal, commentDto));
	}

	@GetMapping("/{recipeId}/comment/{commentId}")
	public ResponseEntity<CommentDto> getCommentForId(@PathVariable int commentId) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(commentService.getComment(commentId));
	}

	@PutMapping("/{recipeId}/comment/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable int commentId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(commentService.updateComment(commentId, commentDto, userPrincipal));
	}

	@DeleteMapping("/{recipeId}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int commentId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
		commentService.deleteComment(commentId, userPrincipal);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<PageRecipeDto> getAllRecipesForUserId(
			@PathVariable String userId,
			@RequestParam(value = "query", defaultValue = "", required = false) String query,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "order", defaultValue = "ASC" , required = false) String order,
			@RequestParam(value = "sort", defaultValue = "id", required = false)  String sort
	) {

		Page<Recipe> resultPage;
		if(query.length() > 0){
			resultPage = recipeService.getAllRecipeForUserAndQuery(query, userId, page, 20, order, sort);
		} else {
			resultPage = recipeService.getAllRecipeForUser(userId ,page, 20, order, sort);

		}

		long totalRecipes = resultPage.getTotalElements();
		long totalPages = resultPage.getTotalPages();

		List<RecipeDto> listRecipes = resultPage.getContent().stream().map(recipeMapper::mapRecipeToDto)
				.collect(toList());

		PageRecipeDto pageRecipeDto = new PageRecipeDto();
		pageRecipeDto.setRecipes(listRecipes);
		pageRecipeDto.setTotalPages(totalPages);
		pageRecipeDto.setTotalRecipes(totalRecipes);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(pageRecipeDto);
	}
}

