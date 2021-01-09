package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.answer.AnswerNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.question.QuestionNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dao.Answer;
import com.dataworks.eventsubscriber.model.dto.AnswerDto;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.service.answer.AnswerService;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.question.QuestionServiceImpl;
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

@WebMvcTest(AnswerController.class)
class AnswerControllerTest {
    @MockBean
    AnswerService answerService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAnswer_Unauthorized() throws Exception {
        //given
        var answerDto = new AnswerDto();
        var json = new ObjectMapper().writeValueAsString(answerDto);
        //when

        //then
        mockMvc.perform(
                post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createInvalidAnswer_BadRequest() throws Exception {
        //given
        var answerDto = new AnswerDto();
        var json = new ObjectMapper().writeValueAsString(answerDto);
        //when

        //then
        mockMvc.perform(
                post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createAnswerWithNotFoundUser_NotFound() throws Exception {
        //given
        var answerDto = new AnswerDto();
        answerDto.setText("Hello sinnasappel!");
        answerDto.setQuestionId(1);
        var json = new ObjectMapper().writeValueAsString(answerDto);

        //when
        when(answerService.store(any(AnswerDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void createAnswerWithNotFoundQuestion_NotFound() throws Exception {
        //given
        var answerDto = new AnswerDto();
        answerDto.setText("Hello sinnasappel!");
        answerDto.setQuestionId(1);
        var json = new ObjectMapper().writeValueAsString(answerDto);

        //when
        when(answerService.store(any(AnswerDto.class))).thenThrow(QuestionNotFoundException.class);

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
    void createAnswerWithFoundUser_Create() throws Exception {
        //given
        var answerDto = new AnswerDto();
        answerDto.setText("Hello sinnasappel!");
        answerDto.setQuestionId(1);
        var json = new ObjectMapper().writeValueAsString(answerDto);

        //when
        when(answerService.store(any(AnswerDto.class))).thenReturn(answerDto);

        //then
        mockMvc.perform(
                post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateAnswer_Unauthorized() throws Exception {
        //given
        var answerDto = new AnswerDto();
        var answerId = 1;

        var json = new ObjectMapper().writeValueAsString(answerDto);
        //when

        //then
        mockMvc.perform(
                put("/api/answers/" + answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateInvalidAnswer_BadRequest() throws Exception {
        //given
        var answerDto = new AnswerDto();
        var answerId = 1;
        var json = new ObjectMapper().writeValueAsString(answerDto);
        //when

        //then
        mockMvc.perform(
                put("/api/answers/" + answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateAnswerWithNotFoundUser_NotFound() throws Exception {
        //given
        var answerId = 1;
        var answerDto = new AnswerDto();
        answerDto.setText("Test");
        answerDto.setQuestionId(1);
        var json = new ObjectMapper().writeValueAsString(answerDto);

        //when
        when(answerService.update(any(Integer.class), any(AnswerDto.class))).thenThrow(UserNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/answers/" + answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateAnswerWithNotFoundAnswer_NotFound() throws Exception {
        //given
        var answerId = 1;
        var answerDto = new AnswerDto();
        answerDto.setText("Test");
        answerDto.setQuestionId(1);
        var json = new ObjectMapper().writeValueAsString(answerDto);

        //when
        when(answerService.update(any(Integer.class), any(AnswerDto.class))).thenThrow(AnswerNotFoundException.class);

        //then
        mockMvc.perform(
                put("/api/answers/" + answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateAnswerSuccess_Update() throws Exception {
        //given
        var answerId = 1;
        var answerDto = new AnswerDto();
        answerDto.setText("Test");
        answerDto.setQuestionId(1);
        var json = new ObjectMapper().writeValueAsString(answerDto);

        //when
        when(answerService.update(any(Integer.class), any(AnswerDto.class))).thenReturn(answerDto);

        //then
        mockMvc.perform(
                put("/api/answers/" + answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}