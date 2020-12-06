package com.dataworks.eventsubscriber.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(int id) {
        super(String.format("Event with id '%s' not found", id));
    }
}
