package com.dataworks.eventsubscriber.service.event;

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
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    public void updateWhenUserHasRoleAdminAndEventIsNotFound_ThrowEventNotFoundException() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(user.isAdmin()).thenReturn(true);
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EventNotFoundException.class)
                .isThrownBy(() -> eventImplService.update(eventId, eventDto));
        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(0)).findByIdAndUser_Id(eventId, userId);
    }

    @Test
    public void updateWhenUserHasRoleUserAndEventIsNotFound_ThrowEventNotFoundException() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(user.isAdmin()).thenReturn(false);
        when(user.getId()).thenReturn(1);
        when(eventRepository.findByIdAndUser_Id(eventId, userId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EventNotFoundException.class)
                .isThrownBy(() -> eventImplService.update(eventId, eventDto));
        verify(eventRepository, times(0)).findById(eventId);
        verify(eventRepository, times(1)).findByIdAndUser_Id(eventId, userId);
    }


    @Test
    public void updateWhenUserHasRoleAdminAndEventIsFound_Update() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(user.isAdmin()).thenReturn(true);
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
    public void updateWhenUserHasRoleUserAndEventIsFound_Update() {
        //given
        var eventId = 1;
        var userId = 1;

        //when
        when(authService.myDaoOrFail()).thenReturn(user);
        when(user.isAdmin()).thenReturn(false);
        when(user.getId()).thenReturn(userId);
        when(eventRepository.findByIdAndUser_Id(eventId, userId)).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.mapToEventDestination(event)).thenReturn(eventDto);


        //then
        var result = eventImplService.update(eventId, eventDto);
        assertThat(result).isInstanceOf(EventDto.class);
        verify(eventRepository, times(0)).findById(eventId);
        verify(eventRepository, times(1)).findByIdAndUser_Id(eventId, userId);
    }

    @Test
    public void retrieveAllEventSortedOnDate() {
        // Given
        var events = new ArrayList<Event>();
        var dateOne = LocalDate.of(2020, 2, 1);
        var dateTwo = LocalDate.of(2020, 2, 2);

        var eventOne = new Event();
        eventOne.setId(1);
        // Don't ask. Java needs a shit ton of help to convert.
        eventOne.setDate(Date.from(dateOne.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        events.add(eventOne);

        var eventTwo = new Event();
        eventTwo.setId(2);
        eventTwo.setDate(Date.from(dateTwo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        events.add(eventTwo);

        // when
        when(eventRepository.findAll(Sort.by("date").descending())).thenReturn(events);

        // then
        var result = eventImplService.findAll();
        assertThat(result.size() == 2).isTrue();
        verify(eventRepository, times(1)).findAll(Sort.by("date").descending());
    }
}