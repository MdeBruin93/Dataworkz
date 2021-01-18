package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.UserBlockDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.service.event.EventService;
import com.dataworks.eventsubscriber.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final EventService eventService;
    private final UserServiceImpl userService;

    @Operation(
            summary = "Get list of all unblocked users",
            description = "Returns a array of user objects.",
            tags = { "Events" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of users.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
            @ApiResponse(responseCode = "401", description = "User is not authorized")})
    @GetMapping("")
    public ResponseEntity findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of all blocked users",
            description = "Returns a array of user objects.",
            tags = { "Events" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of users.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))),
            @ApiResponse(responseCode = "401", description = "User is not authorized")})
    @GetMapping("/blocked")
    public ResponseEntity findAllBlocked() {
        return new ResponseEntity<>(userService.findAllBlocked(), HttpStatus.OK);
    }

    @Operation(
            summary = "Block or Unblock user",
            description = "Returns a array of user objects.",
            tags = { "Events" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of events.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))) })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @Valid @RequestBody UserBlockDto userBlockDto) {
        try {
            return new ResponseEntity(userService.update(id, userBlockDto), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Returns a list of the subscribed events",
            description = "Returns a array of event objects.",
            tags = { "Events" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of events.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
            @ApiResponse(responseCode = "401", description = "User is not authorized") })
    @GetMapping("/subscriptions")
    public ResponseEntity subscriptions() {
        return new ResponseEntity(eventService.findBySubscribedUsers(), HttpStatus.OK);
    }
}