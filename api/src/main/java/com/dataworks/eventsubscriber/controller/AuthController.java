package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.PasswordDontMatchException;
import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.model.dto.*;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import com.dataworks.eventsubscriber.service.token.ResetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Binding;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/my")
    public ResponseEntity my() {
        UserDto userDto = authService.my();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);

        try {
            UserDto userDto = authService.register(registerDto);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        } catch (UserAlreadyExistException userAlreadyExistException) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity resetPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            authService.forgotPassword(forgotPasswordDto.getEmail());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity(authService.resetPassword(resetPasswordDto), HttpStatus.OK);
        } catch (PasswordDontMatchException exception) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/activate")
    public ResponseEntity activate(@Valid @RequestBody TokenDto tokenDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            authService.activate(tokenDto);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserTokenNotFoundException | UserNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
