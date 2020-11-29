package com.dataworks.eventsubscriber.exception.user;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("User already exist!");
    }
}
