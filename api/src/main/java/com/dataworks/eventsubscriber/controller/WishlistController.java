package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.WishlistNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import com.dataworks.eventsubscriber.service.wishlist.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @Operation(
            summary = "Find wishlist by id",
            description = "",
            tags = { "Wishlists" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the found wishlist if any",
                    content = @Content(schema = @Schema(implementation = WishlistDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Logged in user not found or wishlist was not found")})
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        try {
            var wishlist = wishlistService.findByIdAndUserId(id);
            return new ResponseEntity(wishlist, HttpStatus.OK);
        } catch (WishlistNotFoundException wishlistNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Saves a new wishlist",
            description = "",
            tags = { "Wishlists" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wishlist was successfully created",
                    content = @Content(schema = @Schema(implementation = WishlistDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Logged in user not found")})
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity store(@Valid @ModelAttribute WishlistDto wishlistDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var wishlist = wishlistService.store(wishlistDto);
            return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Updates a wishlist",
            description = "",
            tags = { "Wishlists" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist was successfully updated",
                    content = @Content(schema = @Schema(implementation = WishlistDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Logged in user not found or wishlist to update was not found")})
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @Valid @ModelAttribute WishlistDto wishlistDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var event = wishlistService.update(id, wishlistDto);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EventNotFoundException | UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Finds the wishlists for a specific user",
            description = "",
            tags = { "Wishlists" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the wishlists for the logged in user if any",
                    content = @Content(schema = @Schema(implementation = WishlistDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Logged in user not found")})
    @GetMapping("/findbyuser")
    public ResponseEntity findByUser() {
        try {
            return new ResponseEntity(wishlistService.findByUserId(), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Deletes a wishlist",
            description = "",
            tags = { "Wishlists" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletes a wishlist",
                    content = @Content(schema = @Schema(implementation = WishlistDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "404", description = "Either the logged in user is not the owner of the wishlist" +
                    "or the logged in user is not an admin")})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        wishlistService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}