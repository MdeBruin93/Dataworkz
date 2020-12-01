package com.dataworks.eventsubscriber.exception.user;

public class UserNotLoggedIn extends RuntimeException {
    public UserNotLoggedIn() {
        super("User not logged in!");
    }
}
