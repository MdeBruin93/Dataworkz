package com.dataworks.eventsubscriber.exception;

public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(){
        super("Wishlist not found!");
    }
}
