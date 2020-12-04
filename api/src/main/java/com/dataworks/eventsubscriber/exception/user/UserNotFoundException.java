package com.dataworks.eventsubscriber.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found!");
    }

    public UserNotFoundException(String email) {
        super(String.format("User with email '%s' not found!", email));
    }
}
