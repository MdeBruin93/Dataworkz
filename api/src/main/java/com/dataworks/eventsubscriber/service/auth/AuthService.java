package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.ResetPasswordDto;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;

public interface AuthService {
    UserDto register(RegisterDto registerDto);

    UserDto resetPassword(ResetPasswordDto resetPasswordDto);

    UserDto my();

    User myDao();

    User myDaoOrFail();

    TokenDto forgotPassword(String email);
}
