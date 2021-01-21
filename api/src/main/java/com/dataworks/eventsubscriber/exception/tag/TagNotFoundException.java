package com.dataworks.eventsubscriber.exception.tag;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(int id) {
        super(String.format("Tag with id '%s' not found", id));
    }
}
