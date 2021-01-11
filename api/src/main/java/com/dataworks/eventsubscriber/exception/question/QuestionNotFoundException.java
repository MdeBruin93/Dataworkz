package com.dataworks.eventsubscriber.exception.question;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(int id) {
        super(String.format("Question with id '%s' not found", id));
    }
}
