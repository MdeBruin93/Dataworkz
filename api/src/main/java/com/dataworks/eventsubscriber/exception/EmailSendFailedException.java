package com.dataworks.eventsubscriber.exception;

import lombok.Getter;

public class EmailSendFailedException extends RuntimeException {
    @Getter
    private String message;

    public EmailSendFailedException(String message) {
        this.message = message;
    }
}