package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.PasswordDontMatchException;
import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.model.dto.*;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
        summary = "Get information of the logged in user",
        description = "Returns user object with basic autorization.",
        tags = { "Authentication" },
        security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When user is found with the basic autorization credentials then return an user object.",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "When user is not found with the basic autorization credentials.")})
    @GetMapping("/my")
    public ResponseEntity my() {
        UserDto userDto = authService.my();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Update information of the logged in user",
            description = "Returns user object with basic autorization.",
            tags = { "Authentication" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When user is found update",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "When user is not found with the basic autorization credentials.")})
    @PutMapping("/my")
    public ResponseEntity myUpdate(@Valid @RequestBody UserUpdateDto userUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);

        try {
            return new ResponseEntity<>(authService.myUpdate(userUpdateDto), HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Register a new user",
            description = "When user is registered then return a new user object.",
            tags = { "Authentication" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When user is registered then return a new user object",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "409", description = "When emailaddress already exists then return conflict code.")})
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

    @Operation(
            summary = "Forgot password",
            description = "Forgot password",
            tags = { "Authentication" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Return user object and send email",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Model validation failed or Password and repeat password don't not match."),
            @ApiResponse(responseCode = "404", description = "User not foud.")})
    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto, BindingResult bindingResult) {
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

    @Operation(
            summary = "Reset password",
            description = "Reset password",
            tags = { "Authentication" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return user object",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Model validation failed or Password and repeat password don't not match."),
            @ApiResponse(responseCode = "404", description = "User not foud."),
            @ApiResponse(responseCode = "409", description = "Password dont match.")})
    @PostMapping("reset-password")
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

    @Operation(
            summary = "Activate account",
            description = "Activate account",
            tags = { "Authentication" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Model validation failed or Password and repeat password don't not match."),
            @ApiResponse(responseCode = "404", description = "Token not found or user not foud.")})
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
