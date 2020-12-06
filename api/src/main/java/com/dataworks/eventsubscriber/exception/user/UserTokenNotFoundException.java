package com.dataworks.eventsubscriber.exception.user;

public class UserTokenNotFoundException extends RuntimeException {
    public UserTokenNotFoundException() {
        super("UserToken not found!");
    }
}