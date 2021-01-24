package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import com.dataworks.eventsubscriber.service.tag.TagService;
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
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class TagController {
    final TagService tagService;

    @GetMapping("")
    public ResponseEntity findAll(){
        return new ResponseEntity(tagService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(
            summary = "Create or update a tag",
            description = "Add or update a tag for a specific event",
            tags = {"Tags"},
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tag has been created or updated",
                    content = @Content(schema = @Schema(implementation = TagDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Event not found.")
    })
    public ResponseEntity store(@Valid @RequestBody TagDto tagDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var result = tagService.store(tagDto);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (EventNotFoundException eventNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete tag",
            description = "Delete tag with given id.",
            tags = {"Tags"},
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag has been deleted."),
            @ApiResponse(responseCode = "401", description = "User is not authorized")
    })
    public ResponseEntity delete(@PathVariable int id) {
        tagService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}