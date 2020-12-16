package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.model.dto.ForgotPasswordDto;
import com.dataworks.eventsubscriber.service.UserTokenService;
import com.dataworks.eventsubscriber.service.token.ResetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usertokens")
@RequiredArgsConstructor
public class UserTokenController {
    private final UserTokenService userTokenService;
    private final ResetPasswordTokenService resetPasswordTokenService;

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
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need a token in order to verify it.");
        }

        try {
            return new ResponseEntity(userTokenService.verifyTokenForUser(token), HttpStatus.OK);
        } catch (UserTokenNotFoundException utnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserToken was either not found or incorrect.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity resetPassword(@RequestBody ForgotPasswordDto forgotPasswordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            resetPasswordTokenService.setEmail(forgotPasswordDto.getEmail()).generate();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
    }
}