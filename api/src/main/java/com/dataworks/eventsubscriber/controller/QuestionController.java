package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.service.question.QuestionService;
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

import javax.validation.Valid;

@RestController
@RequestMapping("api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @Operation(
            summary = "Create question",
            description = "",
            tags = { "Questions" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the created question",
                    content = @Content(schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "400", description = "Model validation failed."),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Question not found or user not found!")})
    @PostMapping(value = "")
    public ResponseEntity store(@Valid @RequestBody QuestionDto questionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);

        try {
            var question = questionService.store(questionDto);
            return new ResponseEntity<>(question, HttpStatus.CREATED);
        } catch (EventNotFoundException | UserNotFoundException userNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Update question",
            description = "",
            tags = { "Questions" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the updated question",
                    content = @Content(schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "400", description = "Model validation failed."),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Question not found or user not found!")})
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @Valid @RequestBody QuestionDto questionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);

        try {
            var event = questionService.update(id, questionDto);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (QuestionNotFoundException | EventNotFoundException | UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Delete question",
            description = "",
            tags = { "Questions" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deleted the question",
                    content = @Content(schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Question not found or user not found!")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        try {
            questionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (QuestionNotFoundException | UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
