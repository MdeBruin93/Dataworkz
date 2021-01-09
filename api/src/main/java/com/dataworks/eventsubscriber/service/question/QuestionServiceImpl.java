package com.dataworks.eventsubscriber.service.question;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.mapper.QuestionMapper;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.repository.AnswerRepository;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.QuestionRepository;
import com.dataworks.eventsubscriber.service.answer.AnswerService;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final EventRepository eventRepository;
    private final AnswerRepository answerRepository;
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
    public void delete(int id) {
        var authenticatedUser = authService.myDaoOrFail();
        var foundQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        var isAdmin = authenticatedUser.isAdmin();
        var isOwner = authenticatedUser.getId().equals(foundQuestion.getOwner().getId());

        //check user is admin or user is owner of the question
        if (!isAdmin && !isOwner) {
            throw new QuestionNotFoundException(id);
        }

        var answers = foundQuestion.getAnswers();
        answers.forEach(answerRepository::delete);
        questionRepository.delete(foundQuestion);
    }
}
