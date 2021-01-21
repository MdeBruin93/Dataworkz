package com.dataworks.eventsubscriber.service.tag;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.mapper.TagMapper;
import com.dataworks.eventsubscriber.model.dao.Tag;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    final TagRepository tagRepository;
    final EventRepository eventRepository;
    final TagMapper tagMapper;

    @Override
    public TagDto store(TagDto tagDto) {
         var optionalTag = tagRepository.findById(tagDto.getEventId());

        Tag tag;
         if (optionalTag.isPresent()) {
             tag = optionalTag.get();
             tag.setName(tagDto.getName());
         } else {
             tag = new Tag();
             tag.setName(tagDto.getName());
             var event = eventRepository.findById(tagDto.getEventId()).orElseThrow(EventNotFoundException::new);
             tag.setEvent(event);
         }

         tagRepository.save(tag);
         return tagMapper.mapToEventDestination(tag);
    }

    @Override
    public void delete(int id) {
        tagRepository.deleteById(id);
    }
}
