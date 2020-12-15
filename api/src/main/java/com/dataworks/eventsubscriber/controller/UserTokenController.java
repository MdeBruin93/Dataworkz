package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.model.dto.ForgotPasswordDto;
import com.dataworks.eventsubscriber.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usertokens")
@RequiredArgsConstructor
public class UserTokenController {
    private final UserTokenService userTokenService;

    @GetMapping("/gettoken/{email}")
    public ResponseEntity sendEmailVerificationTokenToUser(@PathVariable String email) {
        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need an email in order to verify it.");
        }

        try {
            userTokenService.createEmailTokenForUser(email);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verifyuseremail/{token}")
    public ResponseEntity verifyUserEmail(@PathVariable String token) {
        verifyTokenOrThrowBadRequest(token);

        try {
            return new ResponseEntity(userTokenService.verifyTokenForUser(token), HttpStatus.OK);
        } catch (UserTokenNotFoundException utnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserToken was either not found or incorrect.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity resetPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        try {
            userTokenService.generatePasswordResetToken(forgotPasswordDto);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserTokenNotFoundException utnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserToken was either not found or incorrect.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void verifyTokenOrThrowBadRequest(String token) {
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need a token in order to verify it.");
        }
    }
}