package com.dataworks.eventsubscriber.service.answer;

import com.dataworks.eventsubscriber.exception.answer.AnswerNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.mapper.AnswerMapper;
import com.dataworks.eventsubscriber.model.dto.AnswerDto;
import com.dataworks.eventsubscriber.repository.AnswerRepository;
import com.dataworks.eventsubscriber.repository.QuestionRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final AuthService authService;
    private final AnswerMapper answerMapper;
    @Override
    public AnswerDto store(AnswerDto answerDto) {
        var loggedInUser = authService.myDaoOrFail();
        var foundQuestion = questionRepository.findById(answerDto.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException(answerDto.getQuestionId()));
        var mappedAnswer = answerMapper.mapToEventSource(answerDto);
        mappedAnswer.setQuestion(foundQuestion);
        mappedAnswer.setOwner(loggedInUser);

        return answerMapper.mapToEventDestination(answerRepository.save(mappedAnswer));
    }

    @Override
    public AnswerDto update(int id, AnswerDto answerDto) {
        var loggedInUser = authService.myDaoOrFail();
        var foundAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        var isAdmin = loggedInUser.isAdmin();
        var isOwner = loggedInUser.getId().equals(foundAnswer.getOwner().getId());

        if (!isAdmin && !isOwner) {
            throw new AnswerNotFoundException(id);
        }

        foundAnswer.setText(answerDto.getText());

        return answerMapper.mapToEventDestination(answerRepository.save(foundAnswer));
    }

    @Override
    public void delete(int id) {

    }
}
