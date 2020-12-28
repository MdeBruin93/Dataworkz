package com.dataworks.eventsubscriber.exception.event;

import com.dataworks.eventsubscriber.exception.NotFoundException;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException(int id) {
        super(String.format("Event with id '%s' was not found!", id));
    }

    public EventNotFoundException() {
        super("Event");
    }
}
