package com.clemhlrdt.recipeapp.controllers;


import com.clemhlrdt.recipeapp.dto.RecipeDto;
import com.clemhlrdt.recipeapp.dto.VoteDto;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import com.clemhlrdt.recipeapp.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@AllArgsConstructor
public class VoteController {

	private final VoteService voteService;

	@PostMapping
	public ResponseEntity<RecipeDto> vote(@RequestBody VoteDto voteDto, @AuthenticationPrincipal UserPrincipal user) {

		RecipeDto recipe = voteService.vote(voteDto, user);
		return ResponseEntity.status(HttpStatus.OK).body(recipe);
	}

	@GetMapping("/{recipeId}")
	public ResponseEntity<VoteDto> getVoteForRecipeId(@PathVariable int recipeId, @AuthenticationPrincipal UserPrincipal userPrincipal){

		VoteDto userVote = this.voteService.getVotesForRecipeAndUser(recipeId, userPrincipal);
		return ResponseEntity.status(HttpStatus.OK).body(userVote);
	}
}
