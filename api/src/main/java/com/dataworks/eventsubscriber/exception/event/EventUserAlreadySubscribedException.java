package com.dataworks.eventsubscriber.exception.event;

public class EventUserAlreadySubscribedException extends RuntimeException {
    public EventUserAlreadySubscribedException() {
        super("User is already subscribed to the event!");
    }
}
