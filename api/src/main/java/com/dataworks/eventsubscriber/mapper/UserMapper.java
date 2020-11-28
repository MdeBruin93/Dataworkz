package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserMapper {
    public abstract UserDto mapToDestination(User source);
}
