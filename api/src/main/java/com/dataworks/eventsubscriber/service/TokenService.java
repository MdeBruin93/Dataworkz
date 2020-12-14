package com.dataworks.eventsubscriber.service;

import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    void createEmailTokenForUser(String email);
    String createPasswordResetTokenForUser(String email);
    boolean verifyTokenForUser(String token);
}
