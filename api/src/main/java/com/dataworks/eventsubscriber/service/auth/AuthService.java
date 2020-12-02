package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.AuthenticatedUserDto;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;

public interface AuthService {
    UserDto register(RegisterDto registerDto);

    UserDto my();

    User myDao();
}
