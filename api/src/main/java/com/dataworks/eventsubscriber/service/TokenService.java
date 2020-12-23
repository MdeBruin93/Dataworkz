package com.dataworks.eventsubscriber.service;

import com.dataworks.eventsubscriber.model.dto.ForgotPasswordDto;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    void createEmailTokenForUser(String email);
    String createPasswordResetTokenForUser(String email);
    void generatePasswordResetToken(ForgotPasswordDto forgotPasswordDto);
    boolean verifyTokenForUser(String token);
}
