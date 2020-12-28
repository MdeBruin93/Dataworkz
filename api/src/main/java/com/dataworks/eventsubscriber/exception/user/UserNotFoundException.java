package com.dataworks.eventsubscriber.exception.user;

import com.dataworks.eventsubscriber.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User");
    }

    public UserNotFoundException(String email) {
        super(String.format("User with email '%s' not found!", email));
    }
}
