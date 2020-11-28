package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.model.dto.UserTokenDto;
import com.dataworks.eventsubscriber.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/UserTokens")
@RequiredArgsConstructor
public class UserTokenController {
    UserTokenService userTokenService;

    @GetMapping("/GetToken")
    public void sendEmailVerificationTokenToUser(@RequestParam String email) {
        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need an email in order to verify it.");
        }

        try {
            var token = userTokenService.createEmailTokenForUser(email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/GetToken")
    public boolean verifyUserEmail(@RequestParam String token) {
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need an email in order to verify it.");
        }

        try {
            return userTokenService.verifyEmailTokenForUser(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}