package com.clemhlrdt.recipeapp.controllers;

import com.clemhlrdt.recipeapp.dto.PageRecipeDto;
import com.clemhlrdt.recipeapp.dto.RecipeDto;
import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.exception.UnauthorizedException;
import com.clemhlrdt.recipeapp.mapper.RecipeMapper;
import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import com.clemhlrdt.recipeapp.service.RecipeService;
import com.clemhlrdt.recipeapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final RecipeService recipeService;
	private final RecipeMapper recipeMapper;

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
													 @RequestParam(value = "size", defaultValue = "25", required = false) int size,
													 @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userService.getAllUsers(
						Optional.of(page).orElse(0),
						Optional.of(size).orElse(20),
						Optional.of(sortBy).orElse("id")));
	}

	@GetMapping("/{userId}/recipes")
	public ResponseEntity<PageRecipeDto> getAllRecipeForUser(@PathVariable String userId,
															 @RequestParam(value = "query", defaultValue = "", required = false) String query,
															 @RequestParam(value = "page", defaultValue = "0", required = false) int page,
															 @RequestParam(value = "order", defaultValue = "ASC" , required = false) String order,
															 @RequestParam(value = "sort", defaultValue = "id", required = false)  String sort ){

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

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserByUserId(@PathVariable String userId){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userService.getUserById(userId));
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @Valid @RequestBody UserDto userDto, @AuthenticationPrincipal UserPrincipal userPrincipal){
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userId, userDto, userPrincipal));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId, @AuthenticationPrincipal UserPrincipal userPrincipal){
		userService.deleteUserById(userId, userPrincipal);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{userId}/upload")
	public UserDto uploadFile(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable String userId, @RequestParam("file") MultipartFile file) {
		if(userPrincipal.getId().equals(UUID.fromString(userId))) {
			return userService.setUserAvatar(userId, file);
		} else {
			throw new UnauthorizedException();
		}
	}
}
