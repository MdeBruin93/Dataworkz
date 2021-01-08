package com.dataworks.eventsubscriber.model.dto;

import javax.validation.constraints.NotNull;

public class AnswerDto {
    private int id;
    @NotNull
    private String text;
    @NotNull
    private Integer questionId;
}
