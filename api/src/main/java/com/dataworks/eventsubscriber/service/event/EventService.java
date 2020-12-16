package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.model.dto.EventDto;

import java.util.List;

public interface EventService {
    EventDto store(EventDto eventDto);
    EventDto update(int id, EventDto eventDto);
    List<EventDto> findAll();
    EventDto findById(int id);
    EventDto subscribe(int id);
    List<EventDto> findByUserId();
    List<EventDto> findBySubscribedUsers();
    void delete(int eventId);
}
