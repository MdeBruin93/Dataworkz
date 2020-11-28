package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public abstract class RegisterMapper {
    public abstract User mapToUserSource(RegisterDto destination);
}
