package com.dataworks.eventsubscriber.service.question;


import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    QuestionDto store(QuestionDto questionDto);
    QuestionDto update(int id, QuestionDto questionDto);
    void delete(int id);
}
