package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.tag.TagServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
public class TagControllerTest {
    @MockBean
    TagServiceImpl tagService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "USER")
    void store_StoreTagShouldBeSuccessful() throws Exception {
        //given
        var tagDto = new TagDto();
        tagDto.setName("test");
        var json = new ObjectMapper().writeValueAsString(tagDto);

        //when
        when(tagService.store(any(TagDto.class))).thenReturn(tagDto);

        //then
        mockMvc.perform(
                post("/api/tags/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "USER")
    void store_ShouldThrowEventNotFoundException() throws Exception {
        //given
        var tagDto = new TagDto();
        tagDto.setName("test");
        var json = new ObjectMapper().writeValueAsString(tagDto);

        //when
        when(tagService.store(any(TagDto.class))).thenThrow(new EventNotFoundException());

        //then
        mockMvc.perform(
                post("/api/tags/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void store_ShouldThrowUnauthorized() throws Exception {
        //given
        var tagDto = new TagDto();
        tagDto.setName("test");
        var json = new ObjectMapper().writeValueAsString(tagDto);

        //when

        //then
        mockMvc.perform(
                post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "USER")
    void delete_ShouldSuccessfullyDeleteTag() throws Exception {
        //given
        var tagId = 1;

        //when
        doNothing().when(tagService).delete(any(Integer.class));

        //then
        mockMvc.perform(
                delete("/api/tags/" + tagId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_UnauthorizedUserShouldReturnUnauthorized() throws Exception {
        //given
        var tagId = 1;

        //when

        //then
        mockMvc.perform(
                delete("/api/tags/" + tagId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}