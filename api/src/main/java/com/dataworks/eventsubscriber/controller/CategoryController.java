package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.exception.category.CategoryContainEventsException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.service.category.CategoryService;
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
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = "Create category",
            description = "",
            tags = { "Categories" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the created category",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Logged in user not found")})
    @PostMapping(value = "")
    public ResponseEntity store(@Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var event = categoryService.store(categoryDto);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Update category",
            description = "",
            tags = { "Categories" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the updated category",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Category not found or Logged in user not found")})
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var event = categoryService.update(id, categoryDto);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (NotFoundException notFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Get list of all categories",
            description = "Returns an array of category objects.",
            tags = { "Categories" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of categories.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))) })
    @GetMapping("")
    public ResponseEntity all() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get category",
            description = "Returns the belonging category",
            tags = { "Categories" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))) })
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        try {
            return new ResponseEntity(categoryService.findById(id), HttpStatus.OK);
        } catch (NotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category",
            description = "Delete category with given id.",
            tags = { "Categories" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of categories.",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Category not found."),
            @ApiResponse(responseCode = "409", description = "Category contain events.")})
    public ResponseEntity delete(@PathVariable int id) {
        try {
            categoryService.delete(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (CategoryContainEventsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}