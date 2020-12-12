package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.CommentDto;
import com.clemhlrdt.recipeapp.model.Comment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target="user", source="user")
	@Mapping(target="id", source="id")
	CommentDto mapCommentToDto(Comment comment);

	@InheritInverseConfiguration
	Comment mapCommentDtoToComment(CommentDto commentDto);
}