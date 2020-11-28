package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public UserDto register(RegisterDto registerDto);
}
