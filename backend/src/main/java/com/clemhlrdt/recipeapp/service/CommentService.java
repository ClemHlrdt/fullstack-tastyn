package com.clemhlrdt.recipeapp.service;

import com.clemhlrdt.recipeapp.dto.CommentDto;
import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.exception.ResourceNotFoundException;
import com.clemhlrdt.recipeapp.exception.UnauthorizedException;
import com.clemhlrdt.recipeapp.mapper.CommentMapper;
import com.clemhlrdt.recipeapp.mapper.UserMapper;
import com.clemhlrdt.recipeapp.model.Comment;
import com.clemhlrdt.recipeapp.model.Recipe;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.repository.CommentRepository;
import com.clemhlrdt.recipeapp.repository.RecipeRepository;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import com.clemhlrdt.recipeapp.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

	private final RecipeRepository recipeRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;
	private final UserMapper userMapper;

	@Transactional
	public CommentDto save(Integer recipeId, UserPrincipal userPrincipal, CommentDto commentDto){
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException("recipe", "id", recipeId));

		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userPrincipal.getId()));

		UserDto currentUser = userMapper.mapUserToDto(user);
		commentDto.setUser(currentUser);

		Comment comment = commentMapper.mapCommentDtoToComment(commentDto);

		comment.setRecipe(recipe);
		comment.setUser(user);

		Comment save = commentRepository.save(comment);
		commentDto.setId(save.getId());
		return commentDto;
	}

	// Get one recipe
	@Transactional
	public CommentDto getComment(int id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		return commentMapper.mapCommentToDto(comment);
	}

	// Get all comments for the given recipe
	@Transactional(readOnly = true)
	public List<CommentDto> getAllForRecipe(Integer id) {
		return commentRepository.getAllCommentForRecipeId(id)
				.stream()
				.map(commentMapper::mapCommentToDto)
				.collect(toList());
	}

	@Transactional
	public CommentDto updateComment(int id, CommentDto commentDto, UserPrincipal userPrincipal) {

		Comment commentFound = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

		User userFound = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

		Comment comment = commentMapper.mapCommentDtoToComment(commentDto);

		if(commentFound.getUser().getUsername().equals(userFound.getUsername())){
			commentFound.setComment(comment.getComment());
			return commentMapper.mapCommentToDto(commentFound);
		} else {
			throw new UnauthorizedException();
		}
	}

	@Transactional
	public void deleteComment(int id, @AuthenticationPrincipal UserPrincipal userPrincipal){
		Comment commentFound = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

		User userFound = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

		if(commentFound.getUser().getUsername().equals(userFound.getUsername())){
			commentRepository.delete(commentFound);
		} else {
			throw new UnauthorizedException();
		}
	}
}
