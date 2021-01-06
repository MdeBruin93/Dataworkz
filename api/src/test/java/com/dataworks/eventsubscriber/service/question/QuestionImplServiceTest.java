package com.dataworks.eventsubscriber.service.question;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
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
}