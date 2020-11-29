package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.auth.WebAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @MockBean
    BindingResult bindingResult;
    @MockBean
    WebAuthService webAuthService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerWithValidUser_Register() throws Exception {
        // given
        var userDto = new UserDto();
        var registerDto = new RegisterDto();
        registerDto.setEmail("ricky@hr.nl");
        registerDto.setFirstName("Ricky");
        registerDto.setLastName("van Waas");
        registerDto.setPassword("123456");
        var json = new ObjectMapper().writeValueAsString(registerDto);

        //when
        when(webAuthService.register(any(RegisterDto.class))).thenReturn(userDto);

        // act & assert
        mockMvc.perform(
                post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void registerWithExistingUser_Conflict() throws Exception {
        //given
        var registerDto = new RegisterDto();
        registerDto.setEmail("ricky@hr.nl");
        registerDto.setFirstName("Ricky");
        registerDto.setLastName("van Waas");
        registerDto.setPassword("123456");
        var json = new ObjectMapper().writeValueAsString(registerDto);

        //when
        when(webAuthService.register(any(RegisterDto.class))).thenThrow(UserAlreadyExistException.class);

        // then
        mockMvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void registerWithInvalidUser_BadRequest() throws Exception {
        // given
        var registerDto = new RegisterDto();
        var json = new ObjectMapper().writeValueAsString(registerDto);

        //when

        // then
        mockMvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void myWithExistingUser_My() throws Exception {
        // arrange


        // act & assert
        mockMvc.perform(get("/auth/my"))
                .andExpect(status().isOk());
    }
}