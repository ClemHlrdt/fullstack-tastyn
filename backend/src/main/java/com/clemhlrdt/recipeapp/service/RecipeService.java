package com.clemhlrdt.recipeapp.service;

import com.clemhlrdt.recipeapp.dto.RecipeDto;
import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.exception.ResourceNotFoundException;
import com.clemhlrdt.recipeapp.exception.UnauthorizedException;
import com.clemhlrdt.recipeapp.mapper.RecipeMapper;
import com.clemhlrdt.recipeapp.mapper.UserMapper;
import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.repository.RecipeRepository;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeMapper recipeMapper;
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	// Save a recipe
	@Transactional
	public RecipeDto save(RecipeDto recipeDto,  UserPrincipal userPrincipal) {
		// Transform the RecipeDto to Recipe & save it
		Recipe recipe = recipeMapper.mapRecipeDtoToRecipe(recipeDto);

		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userPrincipal.getId()));
		recipe.setUser(user);


		Recipe save = recipeRepository.save(recipe);
		UserDto currentUser = userMapper.mapUserToDto(save.getUser());
		recipeDto.setUser(currentUser);
		recipeDto.setCreatedAt(save.getCreatedAt());
		recipeDto.setId(save.getId());
		recipeDto.setVotes(recipe.getVotes());
		return recipeDto;
	}

	// Get all recipes
	@Transactional(readOnly = true)
	public Page<Recipe> getAll(int page, int size, String order, String sort) {

		Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));

		return recipeRepository.findAll(paging);
	}

	@Transactional(readOnly = true)
	public Page<Recipe> getAllRecipeForUser(String userId,int page, int size, String order, String sort) {

		Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));

		return recipeRepository.findAllByUserId(UUID.fromString(userId), paging);
	}

	@Transactional(readOnly = true)
	public Page<Recipe> getAllRecipeForUserAndQuery(String query, String userId,int page, int size, String order, String sort) {

		Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));

		return recipeRepository.findByUserIdAndNameContainingIgnoreCase(UUID.fromString(userId), query.toLowerCase(), paging);
	}

	// Search for a recipe
	@Transactional
	public Page<Recipe> searchForRecipeByName(String query, int page, int size, String order, String sort) {

		Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
		return recipeRepository.findByNameContainingIgnoreCase(query, paging);
	}


	// Get one recipe
	@Transactional
	public RecipeDto getRecipe(int id) {
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));
		return recipeMapper.mapRecipeToDto(recipe);
	}

	@Transactional
	public RecipeDto updateRecipe(int id, RecipeDto recipeDto, UserPrincipal userPrincipal) {

		Recipe recipeFound = recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));

		User userFound = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

		if (recipeFound.getUser().getUsername().equals(userFound.getUsername())) {
			recipeMapper.updateRecipeFromDto(recipeDto, recipeFound);
			return recipeMapper.mapRecipeToDto(recipeRepository.save(recipeFound));
		} else {
			throw new UnauthorizedException();
		}
	}

	@Transactional
	public void deleteRecipe(int id, UserPrincipal userPrincipal) {
		Recipe recipeFound = recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));

		User userFound = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

		if (recipeFound.getUser().getUsername().equals(userFound.getUsername())) {
			recipeRepository.delete(recipeFound);
		} else {
			throw new UnauthorizedException();
		}

	}
}
