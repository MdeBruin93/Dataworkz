package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.exception.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.EventMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventImplService implements EventService {
    private final AuthService authService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Override
    public EventDto store(EventDto eventDto) {
        var loggedInUser = authService.myDao();

        if (loggedInUser == null) {
            throw new UserNotFoundException();
        }

        var mappedEvent = eventMapper.mapToEventSource(eventDto);
        mappedEvent.setUser(loggedInUser);

        var savedEvent = eventRepository.save(mappedEvent);

        return eventMapper.mapToEventDestination(savedEvent);
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll(Sort.by("date").descending())
                .stream()
                .map(eventMapper::mapToEventDestination)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findById(int id) {
        var event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        return eventMapper.mapToEventDestination(event);
    }
}
