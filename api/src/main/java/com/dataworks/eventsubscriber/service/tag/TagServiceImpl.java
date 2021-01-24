package com.dataworks.eventsubscriber.service.tag;

import com.dataworks.eventsubscriber.mapper.TagMapper;
import com.dataworks.eventsubscriber.model.dao.Tag;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    final TagRepository tagRepository;
    final EventRepository eventRepository;
    final TagMapper tagMapper;

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll(Sort.by("Name").ascending())
                .stream()
                .map(tagMapper::mapToEventDestination)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto store(TagDto tagDto) {
        var optionalTag = tagRepository.findByName(tagDto.getName());

        Tag tag;
        if (optionalTag.isEmpty()) {
            tag = new Tag();
            tag.setName(tagDto.getName());
            tagRepository.save(tag);
        } else {
            tag = optionalTag.get();
        }

        return tagMapper.mapToEventDestination(tag);
    }

    @Override
    public void delete(int id) {
        tagRepository.deleteById(id);
    }
}
