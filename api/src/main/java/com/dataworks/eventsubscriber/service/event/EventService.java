package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.model.dto.EventDto;

import java.util.List;

public interface EventService {
    EventDto store(EventDto eventDto);
    List<EventDto> findAll();
}
