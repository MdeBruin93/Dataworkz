package com.dataworks.eventsubscriber.service.answer;

import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.AnswerMapper;
import com.dataworks.eventsubscriber.model.dao.Answer;
import com.dataworks.eventsubscriber.model.dao.Question;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.AnswerDto;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.repository.AnswerRepository;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.QuestionRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {
    @Mock
    AnswerRepository answerRepository;
    @Mock
    QuestionRepository questionRepository;
    @Mock
    AuthService authService;
    @Mock
    AnswerMapper answerMapper;
    @InjectMocks
    AnswerServiceImpl answerService;

    @Test
    void storeWhenUserIsNotLoggedIn_ThenThrowException() {
        //given
        var answerDto = new AnswerDto();

        //when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);

        //then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> answerService.store(answerDto));
        verify(authService, times(1)).myDaoOrFail();
        verifyNoMoreInteractions(authService, questionRepository);
    }

    @Test
    void storeWhenQuestionIsNotFound_ThenThrowException() {
        //given
        var loggedInUser = new User();
        var answerDto = new AnswerDto();
        answerDto.setQuestionId(1);
        //when
        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(questionRepository.findById(answerDto.getQuestionId())).thenThrow(QuestionNotFoundException.class);
        //then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> answerService.store(answerDto));
        verify(authService, times(1)).myDaoOrFail();
        verify(questionRepository, times(1)).findById(answerDto.getQuestionId());

    }

    @Test
    void storeWhenSuccess_ThenStore() {
        //given
        var loggedInUser = new User();
        var question = new Question();
        var answer = new Answer();
        var answerDto = new AnswerDto();
        answerDto.setQuestionId(1);
        //when
        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(questionRepository.findById(answerDto.getQuestionId())).thenReturn(Optional.of(question));
        when(answerMapper.mapToEventSource(answerDto)).thenReturn(answer);
        when(answerRepository.save(answer)).thenReturn(answer);
        when(answerMapper.mapToEventDestination(answer)).thenReturn(answerDto);
        //then
        var result = answerService.store(answerDto);
        assertThat(result).isInstanceOf(AnswerDto.class);
        verify(authService, times(1)).myDaoOrFail();
        verify(questionRepository, times(1)).findById(answerDto.getQuestionId());
        verify(answerRepository, times(1)).save(answer);
        verify(answerMapper, times(1)).mapToEventDestination(answer);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}