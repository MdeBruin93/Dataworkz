package com.dataworks.eventsubscriber.service.question;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.QuestionMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.Question;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionImplServiceTest {
    @Mock
    QuestionRepository questionRepository;
    @Mock
    EventRepository eventRepository;
    @Mock
    AuthService authService;
    @Mock
    QuestionMapper questionMapper;
    @InjectMocks
    QuestionImplService questionImplService;

    @Test
    void storeWhenUserIsNotLoggedIn_ThenThrowUserNotFoundException() {
        //given
        var questionDto = new QuestionDto();

        //when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);

        //then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> questionImplService.store(questionDto));
        verify(authService, times(1)).myDaoOrFail();
        verifyNoMoreInteractions(authService, questionRepository);
    }

    @Test
    void storeWhenEventNotExist_ThenThrowEventNotFoundException() {
        //given
        var eventId = 1;
        var questionDto = new QuestionDto();
        questionDto.setEventId(eventId);
        var authenticatedUser = new User();

        //when
        when(authService.myDaoOrFail()).thenReturn(authenticatedUser);
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EventNotFoundException.class)
                .isThrownBy(() -> questionImplService.store(questionDto));
        verify(authService, times(1)).myDaoOrFail();
    }

    @Test
    void storeWhenUserIsLoggedIn_ThenSuccess() {
        //given
        var eventId = 1;
        var questionDto = new QuestionDto();
        questionDto.setEventId(eventId);
        var question = new Question();
        var authenticatedUser = new User();
        var event = new Event();

        //when
        when(authService.myDaoOrFail()).thenReturn(authenticatedUser);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(questionMapper.mapToSource(questionDto)).thenReturn(question);
        when(questionRepository.save(question)).thenReturn(question);

        //then
        questionImplService.store(questionDto);
        verify(authService, times(1)).myDaoOrFail();
        verify(questionMapper, times(1)).mapToSource(questionDto);
        verify(questionMapper, times(1)).mapToDestination(question);
    }

    @Test
    void updateWhenUserIsNotLoggedIn_ThenThrowException() {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        //when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);
        //then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> questionImplService.update(questionId, questionDto));
        verify(authService, times(1)).myDaoOrFail();
        verifyNoMoreInteractions(authService, questionRepository);
    }

    @Test
    void updateWhenQuestionNotFound_ThenThrowException() {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        var user = new User();
        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(questionRepository.findById(questionId)).thenThrow(QuestionNotFoundException.class);

        //then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionImplService.update(questionId, questionDto));
        verify(authService, times(1)).myDaoOrFail();
    }

    @Test
    void updateWhenUserIsNotOwnerAndNotAdmin_ThenThrowException() {
        //given
        var questionId = 1;
        var user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");
        var owner = new User();
        user.setId(2);
        var question = new Question();
        question.setOwner(owner);
        var questionDto = new QuestionDto();

        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        //then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionImplService.update(questionId, questionDto));

    }

    @Test
    void updateWhenUserIsOwner_ThenSave() {
        //given
        var questionId = 1;
        var user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");
        var owner = new User();
        owner.setId(1);
        var question = new Question();
        question.setOwner(owner);
        var questionDto = new QuestionDto();
        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.mapToDestination(question)).thenReturn(questionDto);
        //then
        var result = questionImplService.update(questionId, questionDto);
        assertThat(result).isInstanceOf(QuestionDto.class);
        verify(questionMapper, times(1)).mapToDestination(question);
    }
    @Test
    void updateWhenUserIsAdmin_ThenSave() {
        //given
        var questionId = 1;
        var user = new User();
        user.setId(1);
        user.setRole("ROLE_ADMIN");
        var owner = new User();
        owner.setId(1);
        var question = new Question();
        question.setOwner(owner);
        var questionDto = new QuestionDto();
        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.mapToDestination(question)).thenReturn(questionDto);
        //then
        var result = questionImplService.update(questionId, questionDto);
        assertThat(result).isInstanceOf(QuestionDto.class);
        verify(questionMapper, times(1)).mapToDestination(question);
    }

    @Test
    void deleteWhenUserIsNotLoggedIn_ThenThrowException() {
        //given
        var questionId = 1;
        //when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);
        //then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> questionImplService.delete(questionId));
        verify(authService, times(1)).myDaoOrFail();
        verifyNoMoreInteractions(authService, questionRepository);
    }

    @Test
    void deleteWhenQuestionIsNotFound_ThenThrowException() {
        //given
        var questionId = 1;
        //when
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());
        //then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionImplService.delete(questionId));
        verify(authService, times(1)).myDaoOrFail();
        verify(questionRepository, times(1)).findById(questionId);
    }

    @Test
    void deleteWhenUserIsNotOwnerAndNotAdmin_ThenThrowException() {
        //given
        var questionId = 1;
        var user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");
        var owner = new User();
        user.setId(2);
        var question = new Question();
        question.setOwner(owner);
        var questionDto = new QuestionDto();

        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        //then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionImplService.delete(questionId));

    }

    @Test
    void deleteWhenUserIsOwner_ThenDelete() {
        //given
        var questionId = 1;
        var user = new User();
        user.setId(1);
        user.setRole("ROLE_USER");
        var owner = new User();
        owner.setId(1);
        var question = new Question();
        question.setOwner(owner);
        var questionDto = new QuestionDto();
        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        //then
        questionImplService.delete(questionId);
        verify(questionRepository, times(1)).delete(any(Question.class));
    }
    @Test
    void updateWhenUserIsAdmin_ThenDelete() {
        //given
        var questionId = 1;
        var user = new User();
        user.setId(1);
        user.setRole("ROLE_ADMIN");
        var owner = new User();
        owner.setId(1);
        var question = new Question();
        question.setOwner(owner);
        var questionDto = new QuestionDto();
        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        //then
        questionImplService.delete(questionId);
        verify(questionRepository, times(1)).delete(any(Question.class));
    }
}