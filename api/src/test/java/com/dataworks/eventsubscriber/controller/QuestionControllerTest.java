package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.question.QuestionImplService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {
    @MockBean
    QuestionImplService questionImplService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createEvent_Unauthorized() throws Exception {
        //given
        var questionDto = new QuestionDto();

        var json = new ObjectMapper().writeValueAsString(questionDto);
        //when

        //then
        mockMvc.perform(
                post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createInvalidEvent_BadRequest() throws Exception {
        //given
        var questionDto = new QuestionDto();
        var json = new ObjectMapper().writeValueAsString(questionDto);
        //when

        //then
        mockMvc.perform(
                post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createEventWithNotFoundUser_NotFound() throws Exception {
        //given
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionImplService.store(any(QuestionDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createEventWithNotFoundEvent_NotFound() throws Exception {
        //given
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionImplService.store(any(QuestionDto.class))).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createEventWithFoundUser_Create() throws Exception {
        //given
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionImplService.store(any(QuestionDto.class))).thenReturn(questionDto);

        //then
        mockMvc.perform(
                post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateEvent_Unauthorized() throws Exception {
        //given
        var questionDto = new QuestionDto();
        var questionId = 1;

        var json = new ObjectMapper().writeValueAsString(questionDto);
        //when

        //then
        mockMvc.perform(
                put("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateInvalidEvent_BadRequest() throws Exception {
        //given
        var questionDto = new QuestionDto();
        var questionId = 1;
        var json = new ObjectMapper().writeValueAsString(questionDto);
        //when

        //then
        mockMvc.perform(
                put("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEventWithNotFoundUser_NotFound() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionImplService.update(any(Integer.class), any(QuestionDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEventWithNotFoundEvent_NotFound() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionImplService.update(any(Integer.class), any(QuestionDto.class))).thenThrow(EventNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEventWithNotFoundQuestion_NotFound() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionImplService.update(any(Integer.class), any(QuestionDto.class))).thenThrow(QuestionNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateEventWithFoundUser_Create() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionImplService.update(any(Integer.class), any(QuestionDto.class))).thenReturn(questionDto);

        //then
        mockMvc.perform(
                put("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}