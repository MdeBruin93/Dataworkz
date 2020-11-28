package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.UserTokenDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserTokenMapper {
    public abstract UserTokenDto mapToDestination(UserToken source);
}
