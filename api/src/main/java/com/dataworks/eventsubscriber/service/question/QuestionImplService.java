package com.dataworks.eventsubscriber.service.question;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.mapper.QuestionMapper;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.QuestionRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionImplService implements QuestionService {
    private final QuestionRepository questionRepository;
    private final EventRepository eventRepository;
    private final QuestionMapper questionMapper;
    private final AuthService authService;

    @Override
    public QuestionDto store(QuestionDto questionDto) {
        var authenticatedUser = authService.myDaoOrFail();
        //check event exist or else throw exception
        var foundEvent = eventRepository.findById(questionDto.getEventId())
                .orElseThrow(EventNotFoundException::new);

        var question = questionMapper.mapToSource(questionDto);
        question.setOwner(authenticatedUser);
        question.setEvent(foundEvent);

        return questionMapper.mapToDestination(questionRepository.save(question));
    }

    @Override
    public QuestionDto update(int id, QuestionDto questionDto) {
        return null;
    }

    @Override
    public List<QuestionDto> findAll() {
        return null;
    }

    @Override
    public QuestionDto findById(int id) {
        return null;
    }
}
