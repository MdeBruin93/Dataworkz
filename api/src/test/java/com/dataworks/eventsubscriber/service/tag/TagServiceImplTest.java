package com.dataworks.eventsubscriber.service.tag;

import com.dataworks.eventsubscriber.mapper.TagMapper;
import com.dataworks.eventsubscriber.model.dao.Tag;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import com.dataworks.eventsubscriber.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    TagMapper tagMapper;
    @Mock
    TagRepository tagRepository;
    @InjectMocks
    TagServiceImpl tagService;

    @Test
    void store_whenTagExistsThenUpdate_ShouldBeSuccessful() {
        // given
        var tagName = "Firstname";
        var tagDto = new TagDto();
        tagDto.setName(tagName);
        var tag = new Tag();

        // when
        when(tagRepository.findByName(anyString())).thenReturn(Optional.of(tag));
        when(tagMapper.mapToEventDestination(any(Tag.class))).thenReturn(tagDto);

        // then
        var result = tagService.store(tagDto);
        assertThat(result.getName()).isEqualTo("Firstname");
        verify(tagRepository, times(1)).findByName(tagName);
        verify(tagMapper, times(1)).mapToEventDestination(tag);
    }

    @Test
    void store_whenTagDoesNotExistsThenCreate_ShouldBeSuccessful() {
        // given
        var tagName = "Firstname";
        var tagDto = new TagDto();
        tagDto.setName(tagName);

        // when
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(tagMapper.mapToEventDestination(any(Tag.class))).thenReturn(tagDto);

        // then
        var result = tagService.store(tagDto);
        assertThat(result.getName()).isEqualTo(tagName);
        verify(tagRepository, times(1)).findByName(tagName);
        verify(tagMapper, times(1)).mapToEventDestination(any(Tag.class));
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