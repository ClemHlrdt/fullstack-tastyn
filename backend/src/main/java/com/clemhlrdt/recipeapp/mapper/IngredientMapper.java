package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.IngredientDto;
import com.clemhlrdt.recipeapp.model.Ingredient;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

	IngredientDto mapIngredientToDto(Ingredient ingredient);

	@InheritInverseConfiguration
	Ingredient mapIngredientDtoToIngredient(IngredientDto ingredientDto);
}
