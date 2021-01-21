package com.dataworks.eventsubscriber.service.tag;

import com.dataworks.eventsubscriber.model.dto.TagDto;
import org.springframework.stereotype.Service;

@Service
public interface TagService {
    TagDto store(TagDto tagDto);
    void delete(int id);
}
