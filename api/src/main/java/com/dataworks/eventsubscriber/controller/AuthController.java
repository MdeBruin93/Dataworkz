package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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
}