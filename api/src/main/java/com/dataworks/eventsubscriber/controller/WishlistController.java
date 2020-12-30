package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.WishlistNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import com.dataworks.eventsubscriber.service.wishlist.WishlistService;
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

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        try {
            var wishlist = wishlistService.findByIdAndUserId(id);
            return new ResponseEntity(wishlist, HttpStatus.OK);
        } catch (WishlistNotFoundException wishlistNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @GetMapping("/findbyuser")
    public ResponseEntity findByUser() {
        return new ResponseEntity(wishlistService.findByUserId(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        wishlistService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}