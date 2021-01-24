package com.dataworks.eventsubscriber.service.tag;

import com.dataworks.eventsubscriber.model.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<TagDto> findAll();
    TagDto store(TagDto tagDto);
    void delete(int id);
}
