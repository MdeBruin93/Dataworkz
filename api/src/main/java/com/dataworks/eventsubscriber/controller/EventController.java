package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventUserAlreadySubscribedException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.service.event.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import javax.validation.Valid;

@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Operation(
            summary = "Create event",
            description = "",
            tags = { "Events" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the created event",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "400", description = "Model validation failed."),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Logged in user not found")})
    @PostMapping(value = "")
    public ResponseEntity store(@Valid @RequestBody EventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var event = eventService.store(eventDto);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Update event",
            description = "",
            tags = { "Events" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the updated event",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "400", description = "Model validation failed."),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Event not found or Logged in user not found")})
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @Valid @RequestBody EventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var event = eventService.update(id, eventDto);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EventNotFoundException | UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Get list of all events",
            description = "Returns a array of event objects.",
            tags = { "Events" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of events.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))) })
    @GetMapping("")
    public ResponseEntity all() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get event",
            description = "Returns the belonging event",
            tags = { "Events" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = EventDto.class))) })
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        try {
            return new ResponseEntity(eventService.findById(id), HttpStatus.OK);
        } catch (EventNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Subscribe to event",
            description = "",
            tags = { "Events" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "409", description = "User already subscribed to event",
                    content = @Content(schema = @Schema(implementation = EventDto.class)))})
    @PostMapping("/{id}/subscribe")
    public ResponseEntity subscribe(@PathVariable int id) {
        try {
            return new ResponseEntity(eventService.subscribe(id), HttpStatus.OK);
        } catch (EventNotFoundException | UserNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (EventUserAlreadySubscribedException exception) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/findbyuser")
    @Operation(
            summary = "Get events of logged in user",
            description = "",
            tags = { "Events" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of events.",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")})
    public ResponseEntity findByUser() {
        try {
            return new ResponseEntity(eventService.findByUserId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete event",
            description = "Delete event with given id.",
            tags = { "Events" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of events.",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Event not found.")})
    public ResponseEntity delete(@PathVariable int id) {
        try {
            eventService.delete(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (EventNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}