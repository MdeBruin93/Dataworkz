package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@Component
@NoArgsConstructor
public class AnswerDto {
    private int id;
    @NotNull
    private String text;
    @NotNull
    private Integer questionId;
    private UserDto owner;
}
