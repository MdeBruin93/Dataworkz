package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.service.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/findbyuser")
    public ResponseEntity findByUser(){
        return new ResponseEntity(wishlistService.findByUserId(), HttpStatus.OK);
    }
}
