package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@NoArgsConstructor
@Getter
@Setter
public class QuestionDto extends BaseDto {
    @NotNull
    private String text;
    @NotNull
    private Integer eventId;
    private UserDto owner;
    private List<AnswerDto> answers;
}
