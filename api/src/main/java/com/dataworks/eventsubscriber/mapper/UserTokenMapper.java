package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserTokenMapper {
    public abstract TokenDto mapToDestination(UserToken source);
}
