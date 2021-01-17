package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.Question;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {
    public abstract QuestionDto mapToDestination(Question source);
    public abstract Question mapToSource(QuestionDto destination);
}
