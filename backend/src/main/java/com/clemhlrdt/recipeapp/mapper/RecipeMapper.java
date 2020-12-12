package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.RecipeDto;
import com.clemhlrdt.recipeapp.model.Recipe;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

	// Recipe to Dto
	RecipeDto mapRecipeToDto(Recipe recipe);

	// RecipeDto to Recipe
	@InheritInverseConfiguration
	Recipe mapRecipeDtoToRecipe(RecipeDto recipeDto);

	@Mapping( target = "id", ignore = true )
	@Mapping( target = "votes", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateRecipeFromDto(RecipeDto recipeDto, @MappingTarget Recipe recipe);
}


