package com.dataworks.eventsubscriber.exception.event;

import com.dataworks.eventsubscriber.exception.NotFoundException;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException() {
        super("Event");
    }
}
