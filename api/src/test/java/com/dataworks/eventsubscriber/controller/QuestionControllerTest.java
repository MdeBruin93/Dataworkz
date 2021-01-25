package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.question.QuestionServiceImpl;
import com.dataworks.eventsubscriber.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {
    @MockBean
    QuestionServiceImpl questionServiceImpl;
    @MockBean
    UserService userService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createQuestion_Unauthorized() throws Exception {
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
    void createInvalidQuestion_BadRequest() throws Exception {
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
    void createQuestionWithNotFoundUser_NotFound() throws Exception {
        //given
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionServiceImpl.store(any(QuestionDto.class))).thenThrow(UserNotFoundException.class);

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
    void createQuestionWithNotFoundQuestion_NotFound() throws Exception {
        //given
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionServiceImpl.store(any(QuestionDto.class))).thenThrow(EventNotFoundException.class);

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
    void createQuestionWithFoundUser_Create() throws Exception {
        //given
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionServiceImpl.store(any(QuestionDto.class))).thenReturn(questionDto);

        //then
        mockMvc.perform(
                post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateQuestion_Unauthorized() throws Exception {
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
    void updateInvalidQuestion_BadRequest() throws Exception {
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
    void updateQuestionWithNotFoundUser_NotFound() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionServiceImpl.update(any(Integer.class), any(QuestionDto.class))).thenThrow(UserNotFoundException.class);

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
    void updateQuestionWithNotFoundEvent_NotFound() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionServiceImpl.update(any(Integer.class), any(QuestionDto.class))).thenThrow(EventNotFoundException.class);

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
    void updateQuestionWithNotFoundQuestion_NotFound() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionServiceImpl.update(any(Integer.class), any(QuestionDto.class))).thenThrow(QuestionNotFoundException.class);

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
    void updateQuestionWithFoundUser_Create() throws Exception {
        //given
        var questionId = 1;
        var questionDto = new QuestionDto();
        questionDto.setText("Test");
        questionDto.setEventId(1);
        var json = new ObjectMapper().writeValueAsString(questionDto);

        //when
        when(questionServiceImpl.update(any(Integer.class), any(QuestionDto.class))).thenReturn(questionDto);

        //then
        mockMvc.perform(
                put("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteQuestion_Unauthorized() throws Exception {
        //given
        var questionId = 1;

        //when

        //then
        mockMvc.perform(
                delete("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void deleteQuestionWithNotFoundUser_NotFound() throws Exception {
        //given
        var questionId = 1;
        //when
        doThrow(UserNotFoundException.class).when(questionServiceImpl).delete(questionId);

        //then
        mockMvc.perform(
                delete("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void deleteQuestionWithNotFoundQuestion_NotFound() throws Exception {
        //given
        var questionId = 1;
        //when
        doThrow(UserNotFoundException.class).when(questionServiceImpl).delete(questionId);

        //then
        mockMvc.perform(
                delete("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void deleteQuestionWithFoundUser_Delete() throws Exception {
        //given
        var questionId = 1;

        //when

        //then
        mockMvc.perform(
                delete("/api/questions/" + questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}