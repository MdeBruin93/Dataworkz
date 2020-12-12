package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/verifypasswordreset/{token}")
    public ResponseEntity verifyPasswordReset(@PathVariable String token) {
        verifyTokenOrThrowBadRequest(token);

        try {
            return new ResponseEntity(userTokenService.verifyTokenForUser(token), HttpStatus.OK);
        } catch (UserTokenNotFoundException utnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserToken was either not found or incorrect.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/resetpassword/{email}")
    public ResponseEntity resetPassword(@PathVariable String email) {
        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need an email to send reset password link to.");
        }

        try {
            userTokenService.createPasswordResetTokenForUser(email);
            return new ResponseEntity(HttpStatus.OK);
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