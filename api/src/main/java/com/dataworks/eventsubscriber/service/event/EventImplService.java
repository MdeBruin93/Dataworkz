package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.EventMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventImplService implements EventService {
    private final AuthService authService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Override
    public EventDto store(EventDto eventDto) {
        var loggedInUser = authService.myDaoOrFail();
        var mappedEvent = eventMapper.mapToEventSource(eventDto);
        mappedEvent.setUser(loggedInUser);

        var savedEvent = eventRepository.save(mappedEvent);

        return eventMapper.mapToEventDestination(savedEvent);
    }

    @Override
    public EventDto update(int id, EventDto eventDto) {
        User loggedInUser = authService.myDaoOrFail();
        Optional<Event> eventFromRepo = loggedInUser.isAdmin() ?
                eventRepository.findById(id) :
                eventRepository.findByIdAndUser_Id(id, loggedInUser.getId());

        if (eventFromRepo.isEmpty()) {
            throw new EventNotFoundException();
        }

        Event ev = eventFromRepo.get();
        ev.setDate(eventDto.getDate());
        ev.setDescription(eventDto.getDescription());
        ev.setEuroAmount(eventDto.getEuroAmount());
        ev.setMaxAmountOfAttendees(eventDto.getMaxAmountOfAttendees());
        ev.setTitle(eventDto.getTitle());

        return eventMapper.mapToEventDestination(eventRepository.save(ev));
    }
}
