package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.exception.user.UserNotLoggedIn;
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
        UserDto foundLoggedInUser = null;

        //when
        when(authService.my()).thenReturn(foundLoggedInUser);

        //then
        assertThatExceptionOfType(UserNotLoggedIn.class)
                .isThrownBy(() -> eventImplService.store(eventDto));
        verify(authService, times(1)).my();
        verify(userMapper, times(0)).mapToSource(userDto);
        verify(eventMapper, times(0)).mapToEventSource(eventDto);
        verify(event, times(0)).setUser(user);
        verify(eventRepository, times(0)).save(event);
        verify(eventMapper, times(0)).mapToEventDestination(event);
    }

    @Test
    public void storeWhenUserIsLoggedIn_Store() {
        //given
        UserDto foundLoggedInUser = userDto;

        //when
        when(authService.my()).thenReturn(foundLoggedInUser);
        when(userMapper.mapToSource(foundLoggedInUser)).thenReturn(user);
        when(eventMapper.mapToEventSource(eventDto)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.mapToEventDestination(event)).thenReturn(eventDto);

        //then
        var result = eventImplService.store(eventDto);
        assertThat(result).isInstanceOf(EventDto.class);
        verify(authService, times(1)).my();
        verify(userMapper, times(1)).mapToSource(userDto);
        verify(eventMapper, times(1)).mapToEventSource(eventDto);
        verify(event, times(1)).setUser(user);
        verify(eventRepository, times(1)).save(event);
        verify(eventMapper, times(1)).mapToEventDestination(event);
    }
}