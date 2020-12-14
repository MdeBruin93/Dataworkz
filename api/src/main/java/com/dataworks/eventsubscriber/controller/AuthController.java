package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.PasswordDontMatchException;
import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.ResetPasswordDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;

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

    @PostMapping("reset-password")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity(authService.resetPassword(resetPasswordDto), HttpStatus.OK);
        } catch (UserNotFoundException | PasswordDontMatchException unfe) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
