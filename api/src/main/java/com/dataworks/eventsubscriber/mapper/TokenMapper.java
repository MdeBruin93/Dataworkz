package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.ResetPasswordDto;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TokenMapper {
    public abstract TokenDto mapResetPasswordDtoToSource(ResetPasswordDto resetPasswordDto);
}
