package com.dataworks.eventsubscriber.exception.answer;

import com.dataworks.eventsubscriber.exception.NotFoundException;

public class AnswerNotFoundException extends NotFoundException {
    public AnswerNotFoundException(int id) {
        super(String.format("Answer with id '%s' was not found!", id));
    }
}
