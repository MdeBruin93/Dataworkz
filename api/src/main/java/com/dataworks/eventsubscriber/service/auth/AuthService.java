package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.*;

public interface AuthService {
    UserDto register(RegisterDto registerDto);

    UserDto resetPassword(ResetPasswordDto resetPasswordDto);

    UserDto my();

    User myDao();

    User myDaoOrFail();

    UserDto myUpdate(UserUpdateDto userDto);

    TokenDto forgotPassword(String email);

    UserDto activate(TokenDto tokenDto);
}
