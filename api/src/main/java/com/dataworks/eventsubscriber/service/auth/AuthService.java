package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;

public interface AuthService {
    UserDto register(RegisterDto registerDto);
}