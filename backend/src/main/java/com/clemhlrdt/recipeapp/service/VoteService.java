package com.clemhlrdt.recipeapp.service;


import com.clemhlrdt.recipeapp.dto.RecipeDto;
import com.clemhlrdt.recipeapp.dto.VoteDto;
import com.clemhlrdt.recipeapp.exception.ApiRequestException;
import com.clemhlrdt.recipeapp.exception.ResourceNotFoundException;
import com.clemhlrdt.recipeapp.mapper.RecipeMapper;
import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.model.RecipeVote;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.repository.RecipeRepository;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import com.clemhlrdt.recipeapp.repository.VoteRepository;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final RecipeRepository recipeRepository;
	private final UserRepository userRepository;
	private final RecipeMapper recipeMapper;

	@Transactional
	public RecipeDto vote(VoteDto voteDto, UserPrincipal userPrincipal) {

		/*
			Fetch user & recipe
		 */
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userPrincipal.getId()));

		Recipe recipe = recipeRepository.findById(voteDto.getRecipeId())
				.orElseThrow(() -> new ResourceNotFoundException("Recipe",
						"id",
						voteDto.getRecipeId()));

		RecipeVote recipeVote = setNewVote(voteDto, recipe, user);

		voteRepository.save(recipeVote);
		Optional<Integer> votes = voteRepository.sumVotesByRecipe(voteDto.getRecipeId());
		votes.ifPresent(recipe::setVotes);

		recipeRepository.save(recipe);
		return recipeMapper.mapRecipeToDto(recipe);
	}

	public VoteDto getVotesForRecipeAndUser(int recipeId, UserPrincipal userPrincipal){

		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe",
						"id",
						recipeId));

		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userPrincipal.getId()));

		RecipeVote recipeVote = checkIfPreviousVoteForRecipeAndUser(recipe, user);

		if(recipeVote != null){
			return new VoteDto(recipeVote.getValue(), recipe.getId());
		}
		return new VoteDto(0, recipe.getId());
	}

	private RecipeVote setNewVote(VoteDto vote, Recipe recipe, User user){
		RecipeVote previousVote = this.checkIfPreviousVoteForRecipeAndUser(recipe, user);

		RecipeVote newVote;

		if(previousVote == null){
			newVote = generateVote(vote, recipe, user);
		} else {
			int newVoteValue = vote.getValue();
			voteRepository.deleteVoteByRecipeAndUser(recipe, user);
			if(newVoteValue != previousVote.getValue()){
				newVote = generateVote(vote, recipe, user);
			} else {
				VoteDto replacementVote = new VoteDto(0, recipe.getId());
				newVote = generateVote(replacementVote, recipe, user);
			}
		}

		return newVote;
	}

	private RecipeVote checkIfPreviousVoteForRecipeAndUser(Recipe recipe, User user){
		Optional<RecipeVote> voteByRecipeAndUser = voteRepository.findVoteByRecipeAndUser(recipe, user);
		return voteByRecipeAndUser.orElse(null);
	}

	private RecipeVote generateVote(VoteDto vote, Recipe recipe, User user){
		int voteValue;

		if(vote.getValue() == 1){
			voteValue = 1;
		} else if(vote.getValue() == -1){
			voteValue = -1;
		} else if(vote.getValue() == 0){
			voteValue = 0;
		} else {
			throw new ApiRequestException("Invalid vote value");
		}

		return RecipeVote.builder()
				.value(voteValue)
				.recipe(recipe)
				.user(user)
				.build();
	}
}

