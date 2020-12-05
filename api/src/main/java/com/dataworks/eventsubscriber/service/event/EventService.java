package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dto.EventDto;

public interface EventService {
    public EventDto store(EventDto eventDto);
    public EventDto update(int id, EventDto eventDto);
    EventDto store(EventDto eventDto);
    List<EventDto> findAll();
}
