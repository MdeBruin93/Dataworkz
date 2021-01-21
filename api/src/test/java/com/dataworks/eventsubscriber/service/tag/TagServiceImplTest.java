package com.dataworks.eventsubscriber.service.tag;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.mapper.TagMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.Tag;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    TagMapper tagMapper;
    @Mock
    EventRepository eventRepository;
    @Mock
    TagRepository tagRepository;
    @InjectMocks
    TagServiceImpl tagService;

    @Test
    void store_whenTagExistsThenUpdate_ShouldBeSuccessful() {
        // given
        var tagId = 1;
        var eventId = 1;
        var tagDto = new TagDto();
        tagDto.setId(tagId);
        tagDto.setName("Firstname");
        tagDto.setEventId(eventId);
        var tag = new Tag();

        // when
        when(tagRepository.findById(anyInt())).thenReturn(Optional.of(tag));
        when(tagMapper.mapToEventDestination(any(Tag.class))).thenReturn(tagDto);

        // then
        var result = tagService.store(tagDto);
        assertThat(result.getName()).isEqualTo("Firstname");
        verify(tagRepository, times(1)).findById(tagId);
        verify(tagMapper, times(1)).mapToEventDestination(tag);
    }

    @Test
    void store_whenTagDoesNotExistsThenCreate_ShouldBeSuccessful() {
        // given
        var tagId = 1;
        var eventId = 1;
        var tagDto = new TagDto();
        tagDto.setId(tagId);
        tagDto.setName("Firstname");
        tagDto.setEventId(eventId);

        // when
        when(tagRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(new Event()));
        when(tagMapper.mapToEventDestination(any(Tag.class))).thenReturn(tagDto);

        // then
        var result = tagService.store(tagDto);
        assertThat(result.getName()).isEqualTo("Firstname");
        verify(tagRepository, times(1)).findById(tagId);
        verify(eventRepository, times(1)).findById(eventId);
        verify(tagMapper, times(1)).mapToEventDestination(any(Tag.class));
    }

    @Test
    void store_createTagWhileEventDoesNotExist_ShouldThrowEventNotFoundException() {
        // given
        var tagId = 1;
        var eventId = 1;
        var tagDto = new TagDto();
        tagDto.setId(tagId);
        tagDto.setName("Firstname");
        tagDto.setEventId(eventId);

        // when
        when(tagRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(EventNotFoundException.class)
                .isThrownBy(() -> tagService.store(tagDto));
        verify(tagRepository, times(1)).findById(tagId);
        verify(eventRepository, times(1)).findById(anyInt());
    }

    @Test
    void delete() {
        // given
        var tagId = 1;

        // when
        // then
        tagService.delete(tagId);
    }
}