package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class EventMapper {
    public abstract Event mapToEventSource(EventDto destination);

    public abstract EventDto mapToEventDestination(Event savedEvent);
}
