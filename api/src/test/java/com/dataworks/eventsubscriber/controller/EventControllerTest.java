package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.event.EventImplService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {
    @MockBean
    EventImplService eventImplService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createInvalidEvent_BadRequest() throws Exception {
        //given
        var eventDto = new EventDto();
        var json = new ObjectMapper().writeValueAsString(eventDto);
        //when

        //then
        mockMvc.perform(
                post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEventWithNotFoundUser_NotFound() throws Exception {
        //given
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventImplService.store(any(EventDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createEventWithFoundUser_Create() throws Exception {
        //given
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventImplService.store(any(EventDto.class))).thenReturn(eventDto);

        //then
        mockMvc.perform(
                post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateInvalidEvent_BadRequest() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        var json = new ObjectMapper().writeValueAsString(eventDto);
        //when

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEventWithNotFoundUser_NotFound() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventImplService.update(eq(id), any(EventDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEventWithNotFoundEvent_NotFound() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventImplService.update(eq(id), any(EventDto.class))).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEvent_Update() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventImplService.update(eq(id), any(EventDto.class))).thenReturn(eventDto);

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}