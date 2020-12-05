package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dto.EventDto;

import java.util.List;

public interface EventService {
    EventDto store(EventDto eventDto);
    EventDto update(int id, EventDto eventDto);
    List<EventDto> findAll();
}
