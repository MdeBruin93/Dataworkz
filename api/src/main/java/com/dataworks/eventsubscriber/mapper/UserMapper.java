package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.AuthenticatedUserDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserDto mapToDestination(User source);
    public abstract UserDto mapToDestination(AuthenticatedUserDto authenticatedUserDto);
    public abstract AuthenticatedUserDto mapToAuthenticatedUserDestination(User source);
    public abstract User mapToSource(UserDto destination);
    public abstract User mapToSource(AuthenticatedUserDto destination);
}
