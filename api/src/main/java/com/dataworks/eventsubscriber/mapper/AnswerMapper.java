package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.Answer;
import com.dataworks.eventsubscriber.model.dto.AnswerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AnswerMapper {
    public abstract Answer mapToEventSource(AnswerDto destination);
    public abstract AnswerDto mapToEventDestination(Answer savedEvent);
}
