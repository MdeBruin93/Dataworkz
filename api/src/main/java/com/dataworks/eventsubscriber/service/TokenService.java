package com.dataworks.eventsubscriber.service;

import com.dataworks.eventsubscriber.model.dto.UserTokenDto;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    UserTokenDto createEmailTokenForUser(String email);
    boolean verifyEmailTokenForUser(String token);
}
