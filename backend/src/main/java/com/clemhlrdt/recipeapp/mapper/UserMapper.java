package com.clemhlrdt.recipeapp.mapper;

import com.clemhlrdt.recipeapp.dto.UserDto;
import com.clemhlrdt.recipeapp.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// User to Dto
	UserDto mapUserToDto(User user);

	// UserDto to User
	@InheritInverseConfiguration
	User mapUserDtoToUser(UserDto userDto);

	@Mapping( target = "id", ignore = true )
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
