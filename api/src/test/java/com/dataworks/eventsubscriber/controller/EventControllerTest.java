package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.category.CategoryNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventUserAlreadySubscribedException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.event.EventServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {
    @MockBean
    EventServiceImpl eventServiceImpl;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createEvent_Unauthorized() throws Exception {
        //given
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);
        //when

        //then
        mockMvc.perform(
                post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createInvalidEvent_BadRequest() throws Exception {
        //given
        var eventDto = new EventDto();
        var json = new ObjectMapper().writeValueAsString(eventDto);
        //when

        //then
        mockMvc.perform(
                post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createEventWithNotFoundCategory_NotFound() throws Exception {
        //given
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventServiceImpl.store(any(EventDto.class))).thenThrow(CategoryNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createEventWithNotFoundUser_NotFound() throws Exception {
        //given
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventServiceImpl.store(any(EventDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createEventWithFoundUser_Create() throws Exception {
        //given
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventServiceImpl.store(any(EventDto.class))).thenReturn(eventDto);

        //then
        mockMvc.perform(
                post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateInvalidEvent_Unauthorized() throws Exception {
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
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
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
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEventWithNotFoundCategory_NotFound() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventServiceImpl.update(eq(id), any(EventDto.class))).thenThrow(CategoryNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEventWithNotFoundUser_NotFound() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventServiceImpl.update(eq(id), any(EventDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEventWithNotFoundEvent_NotFound() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventServiceImpl.update(eq(id), any(EventDto.class))).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEvent_Update() throws Exception {
        //given
        var id = 1;
        var eventDto = new EventDto();
        eventDto.setTitle("Test");
        eventDto.setDate(new Date());
        eventDto.setDescription("Test");
        eventDto.setEuroAmount(5);
        eventDto.setMaxAmountOfAttendees(1);
        eventDto.setImageUrl("test.png");
        var json = new ObjectMapper().writeValueAsString(eventDto);

        //when
        when(eventServiceImpl.update(eq(id), any(EventDto.class))).thenReturn(eventDto);

        //then
        mockMvc.perform(
                put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void allWhenSuccess_ThenReturnOk() throws Exception {
        //given
        var event = new EventDto();
        var events = new ArrayList<EventDto>();
        events.add(event);
        //when
        when(eventServiceImpl.findAll()).thenReturn(events);

        //then
        mockMvc.perform(
                get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findByIdWhenEventNotFound_ThenReturnNotFound() throws Exception {
        //given
        var eventId = 1;

        //when
        when(eventServiceImpl.findById(eventId)).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                get("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findByIdWhenSuccess_ThenReturnOk() throws Exception {
        //given
        var eventId = 1;
        var event = new EventDto();

        //when
        when(eventServiceImpl.findById(eventId)).thenReturn(event);

        //then
        mockMvc.perform(
                get("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void subscribeEvent_EventUnauthorized() throws Exception {
        //given
        var eventId = 1;
        //when
        when(eventServiceImpl.subscribe(eventId)).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/events/" + eventId + "/subscribe"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void subscribeEvent_EventNotFound() throws Exception {
        //given
        var eventId = 1;
        //when
        when(eventServiceImpl.subscribe(eventId)).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/events/" + eventId + "/subscribe"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void subscribeEvent_UserNotFound() throws Exception {
        //given
        var eventId = 1;
        //when
        when(eventServiceImpl.subscribe(eventId)).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/events/" + eventId + "/subscribe"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void subscribeEvent_UserAlreadySubscribed() throws Exception {
        //given
        var eventId = 1;
        //when
        when(eventServiceImpl.subscribe(eventId)).thenThrow(EventUserAlreadySubscribedException.class);

        //then
        mockMvc.perform(
                post("/api/events/" + eventId + "/subscribe"))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void subscribeEvent_Subscribe() throws Exception {
        //given
        var eventId = 1;
        //when
        when(eventServiceImpl.subscribe(eventId)).thenReturn(new EventDto());

        //then
        mockMvc.perform(
                post("/api/events/" + eventId + "/subscribe"))
                .andExpect(status().isOk());
    }

    @Test
    void findByUser_Unauthorized() throws Exception {
        //given

        //when
        when(eventServiceImpl.findByUserId()).thenReturn(new ArrayList<EventDto>());

        //then
        mockMvc.perform(
                get("/api/events/findbyuser"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void findByUser_Exception() throws Exception {
        //given

        //when
        when(eventServiceImpl.findByUserId()).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                get("/api/events/findbyuser"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void findByUser_Success() throws Exception {
        //given

        //when
        when(eventServiceImpl.findByUserId()).thenReturn(new ArrayList<EventDto>());

        //then
        mockMvc.perform(
                get("/api/events/findbyuser"))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_Unauthorized() throws Exception {
        // given
        var eventId = 1;
        var json = new ObjectMapper().writeValueAsString(new EventDto());

        // when

        // then
        mockMvc.perform(
                delete("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    public void delete_NotFound() throws Exception {
        // given
        var eventId = 1;
        var json = new ObjectMapper().writeValueAsString(new EventDto());

        // when
        doThrow(EventNotFoundException.class).when(eventServiceImpl).delete(eventId);

        // then
        mockMvc.perform(
                delete("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    public void delete_Success() throws Exception {
        // given
        var eventId = 1;
        var json = new ObjectMapper().writeValueAsString(new EventDto());

        // when

        // then
        mockMvc.perform(
                delete("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}