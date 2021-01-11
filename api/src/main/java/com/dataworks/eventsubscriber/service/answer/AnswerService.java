package com.dataworks.eventsubscriber.service.answer;

import com.dataworks.eventsubscriber.model.dto.AnswerDto;
import org.springframework.stereotype.Service;

@Service
public interface AnswerService {
    AnswerDto store(AnswerDto answerDto);
    AnswerDto update(int id, AnswerDto answerDto);
    void delete(int id);
}
