package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.EventMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.dataworks.eventsubscriber.model.dao.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventImplServiceTest {
    @Mock
    AuthService authService;
    @Mock
    EventRepository eventRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    EventMapper eventMapper;
    @Mock
    User user;
    @Mock
    Event event;
    @Mock
    UserDto userDto;
    @Mock
    EventDto eventDto;
    @InjectMocks
    EventImplService eventImplService;

    @Test
    public void storeWhenUserIsNotLoggedIn_ThrowUserIsNotLoggedInException() {
        //given
        User foundLoggedInUser = null;

        //when
        when(authService.myDao()).thenReturn(foundLoggedInUser);

        //then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> eventImplService.store(eventDto));
        verify(authService, times(1)).myDao();
        verify(userMapper, times(0)).mapToSource(userDto);
        verify(eventMapper, times(0)).mapToEventSource(eventDto);
        verify(event, times(0)).setUser(user);
        verify(eventRepository, times(0)).save(event);
        verify(eventMapper, times(0)).mapToEventDestination(event);
    }

    @Test
    public void storeWhenUserIsLoggedIn_Store() {
        //given
        User foundLoggedInUser = user;

        //when
        when(authService.myDao()).thenReturn(foundLoggedInUser);
        when(eventMapper.mapToEventSource(eventDto)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.mapToEventDestination(event)).thenReturn(eventDto);

        //then
        var result = eventImplService.store(eventDto);
        assertThat(result).isInstanceOf(EventDto.class);
        verify(authService, times(1)).myDao();
        verify(eventMapper, times(1)).mapToEventSource(eventDto);
        verify(event, times(1)).setUser(user);
        verify(eventRepository, times(1)).save(event);
        verify(eventMapper, times(1)).mapToEventDestination(event);
    }

    @Test
    void updateWhenUserHasRoleAdminAndEventIsNotFound_ThrowEventNotFoundException() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDao()).thenReturn(user);
        when(user.isAdmin()).thenReturn(true);
        when(eventDto.getId()).thenReturn(eventId);
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EventNotFoundException.class)
                .isThrownBy(() -> eventImplService.update(eventId, eventDto));
        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(0)).findByIdAndUser_Id(eventId, userId);
    }

    @Test
    void updateWhenUserHasRoleUserAndEventIsNotFound_ThrowEventNotFoundException() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDao()).thenReturn(user);
        when(user.isAdmin()).thenReturn(false);
        when(user.getId()).thenReturn(1);
        when(eventDto.getId()).thenReturn(eventId);
        when(eventRepository.findByIdAndUser_Id(eventId, userId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EventNotFoundException.class)
                .isThrownBy(() -> eventImplService.update(eventId, eventDto));
        verify(eventRepository, times(0)).findById(eventId);
        verify(eventRepository, times(1)).findByIdAndUser_Id(eventId, userId);
    }


    @Test
    void updateWhenUserHasRoleAdminAndEventIsFound_Update() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDao()).thenReturn(user);
        when(user.isAdmin()).thenReturn(true);
        when(eventDto.getId()).thenReturn(eventId);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.mapToEventDestination(event)).thenReturn(eventDto);


        //then
        var result = eventImplService.update(eventId, eventDto);
        assertThat(result).isInstanceOf(EventDto.class);
        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(0)).findByIdAndUser_Id(eventId, userId);
    }

    @Test
    void updateWhenUserHasRoleUserAndEventIsFound_Update() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDao()).thenReturn(user);
        when(user.isAdmin()).thenReturn(false);
        when(user.getId()).thenReturn(userId);
        when(eventDto.getId()).thenReturn(eventId);
        when(eventRepository.findByIdAndUser_Id(eventId, userId)).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.mapToEventDestination(event)).thenReturn(eventDto);


        //then
        var result = eventImplService.update(eventId, eventDto);
        assertThat(result).isInstanceOf(EventDto.class);
        verify(eventRepository, times(0)).findById(eventId);
        verify(eventRepository, times(1)).findByIdAndUser_Id(eventId, userId);
    }
}