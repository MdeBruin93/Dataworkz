package com.dataworks.eventsubscriber.service.question;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
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
        var authenticatedUser = authService.myDaoOrFail();
        var foundQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        var isAdmin = authenticatedUser.isAdmin();
        var isOwner = authenticatedUser.getId().equals(foundQuestion.getOwner().getId());

        //check user is admin or user is owner of the question
        if (!isAdmin && !isOwner) {
            throw new QuestionNotFoundException(id);
        }

        foundQuestion.setText(questionDto.getText());

        return questionMapper.mapToDestination(questionRepository.save(foundQuestion));
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
