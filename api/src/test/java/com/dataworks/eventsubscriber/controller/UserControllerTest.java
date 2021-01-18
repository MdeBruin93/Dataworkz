package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.QuestionDto;
import com.dataworks.eventsubscriber.model.dto.UserBlockDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.event.EventImplService;
import com.dataworks.eventsubscriber.service.event.EventService;
import com.dataworks.eventsubscriber.service.user.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    EventImplService eventImplService;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllWhenNotLoggedIn_ThenUnauthorized() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void findAllWhenUserIsNotAdmin_ThenUnauthorized() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "ADMIN")
    void findAllSuccess_ThenFindAll() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findAllBlockedWhenNotLoggedIn_ThenUnauthorized() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                get("/api/users/blocked")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void findAllBlockedWhenUserIsNotAdmin_ThenUnauthorized() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                get("/api/users/blocked")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "ADMIN")
    void findAllBlockedSuccess_ThenFindAll() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                get("/api/users/blocked")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateWhenUserNotLoggedIn_ThenUnauthorized() throws Exception {
        //given
        var userId = 1;
        var userBlockDto = new UserBlockDto();
        userBlockDto.setBlocked(true);
        var userDto = new UserDto();
        var json = new ObjectMapper().writeValueAsString(userBlockDto);

        //when
        //then
        mockMvc.perform(
                put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void updateWhenUserIsNotAdmin_ThenForbidden() throws Exception {
        //given
        var userId = 1;
        var userBlockDto = new UserBlockDto();
        userBlockDto.setBlocked(true);
        var userDto = new UserDto();
        var json = new ObjectMapper().writeValueAsString(userBlockDto);
        //when
        //then
        mockMvc.perform(
                put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "ADMIN")
    void updateWhenUserNotFound_ThenNotFound() throws Exception {
        //given
        var userId = 1;
        var userBlockDto = new UserBlockDto();
        userBlockDto.setBlocked(true);
        var userDto = new UserDto();
        var json = new ObjectMapper().writeValueAsString(userBlockDto);
        //when
        when(userService.update(anyInt(), any(UserBlockDto.class))).thenThrow(UserNotFoundException.class);
        //then
        mockMvc.perform(
                put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "ADMIN")
    void updateWhenSuccess_ThenUpdate() throws Exception {
        //given
        var userId = 1;
        var userBlockDto = new UserBlockDto();
        userBlockDto.setBlocked(true);
        var userDto = new UserDto();

        var json = new ObjectMapper().writeValueAsString(userBlockDto);
        //when
        when(userService.update(anyInt(), any(UserBlockDto.class))).thenReturn(userDto);
        //then
        mockMvc.perform(
                put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}