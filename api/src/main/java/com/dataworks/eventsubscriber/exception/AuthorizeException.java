package com.dataworks.eventsubscriber.exception;

public class AuthorizeException extends RuntimeException {
    public AuthorizeException() {
        super("User authorize exception");
    }
}
