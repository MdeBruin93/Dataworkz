package com.dataworks.eventsubscriber.exception;

public class PasswordDontMatchException extends RuntimeException {
    public PasswordDontMatchException() {
        super("The password don't match.");
    }
}
