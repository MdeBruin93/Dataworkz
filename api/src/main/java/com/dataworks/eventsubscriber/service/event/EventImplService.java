package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.exception.user.UserNotLoggedIn;
import com.dataworks.eventsubscriber.mapper.EventMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.auth.WebAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventImplService implements EventService {
    private final AuthService authService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Override
    public EventDto store(EventDto eventDto) {
        var loggedInUser = authService.my();

        if (loggedInUser == null) {
            throw new UserNotLoggedIn();
        }

        var mappedLoggedInUser = userMapper.mapToSource(loggedInUser);
        var mappedEvent = eventMapper.mapToEventSource(eventDto);
        mappedEvent.setUser(mappedLoggedInUser);

        var savedEvent = eventRepository.save(mappedEvent);

        return eventMapper.mapToEventDestination(savedEvent);
    }
}
