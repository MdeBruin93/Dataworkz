package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.model.dto.UserTokenDto;
import com.dataworks.eventsubscriber.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/usertokens")
@RequiredArgsConstructor
public class UserTokenController {
    private final UserTokenService userTokenService;

    @GetMapping("/gettoken/{email}")
    public void sendEmailVerificationTokenToUser(@PathVariable String email) {
        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need an email in order to verify it.");
        }

        try {
            var token = userTokenService.createEmailTokenForUser(email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verifyuseremail/{token}")
    public boolean verifyUserEmail(@PathVariable String token) {
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need an email in order to verify it.");
        }

        try {
            return userTokenService.verifyEmailTokenForUser(token);
        } catch (UserTokenNotFoundException utnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserToken was either not found or incorrect.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}