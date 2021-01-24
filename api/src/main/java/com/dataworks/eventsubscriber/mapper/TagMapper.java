package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.Tag;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TagMapper {
    public abstract Tag mapToEventSource(TagDto destination);
    public abstract TagDto mapToEventDestination(Tag savedTag);
    public abstract List<TagDto> mapToEventDestinationCollection(List<Tag> savedTags);
}
