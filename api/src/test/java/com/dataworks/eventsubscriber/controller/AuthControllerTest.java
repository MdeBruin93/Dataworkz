package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.PasswordDontMatchException;
import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.model.dto.*;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.auth.WebAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.*;
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
    @Mock
    UserDto userDto;
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
                post("/api/auth/register")
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
                post("/api/auth/register")
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
                post("/api/auth/register")
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
        mockMvc.perform(get("/api/auth/my"))
                .andExpect(status().isOk());
    }

    @Test
    void forgotPasswordWhenRequestBodyIsNotValid_ThenThrowBadRequest() throws Exception {
        //given
        var forgotPasswordDto = new ForgotPasswordDto();
        var json = new ObjectMapper().writeValueAsString(forgotPasswordDto);
        //when
        //then
        mockMvc.perform(
                post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void forgotPasswordWhenUserIsNotFound_ThenThrowNotFoundRequest() throws Exception {
        //given
        var email = "info@hr.nl";
        var forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail(email);

        var json = new ObjectMapper().writeValueAsString(forgotPasswordDto);
        //when
        when(webAuthService.forgotPassword(email)).thenThrow(UserNotFoundException.class);
        //then
        mockMvc.perform(
                post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void forgotPasswordWhenSuccess_ThenReturnNoContentRequest() throws Exception {
        //given
        var email = "info@hr.nl";
        var forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmail(email);

        var json = new ObjectMapper().writeValueAsString(forgotPasswordDto);
        //when
        //then
        mockMvc.perform(
                post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void resetPasswordWhenRequestBodyIsNotValid_ThenThrowBadRequest() throws Exception {
        //given
        var forgotPasswordDto = new ForgotPasswordDto();
        var json = new ObjectMapper().writeValueAsString(forgotPasswordDto);
        //when
        //then
        mockMvc.perform(
                post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void resetPasswordWhenUserIsNotFound_ThenThrowNotFoundRequest() throws Exception {
        //given
        var token = "abc";
        var password = "123456";
        var resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setToken(token);
        resetPasswordDto.setNewPassword(password);
        resetPasswordDto.setRepeatNewPassword(password);
        var json = new ObjectMapper().writeValueAsString(resetPasswordDto);
        //when
        when(webAuthService.resetPassword(isA(ResetPasswordDto.class)))
                .thenThrow(UserNotFoundException.class);
        //then
        mockMvc.perform(
                post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void resetPasswordWhenPasswordDontMatch_ThenThrowBadRequest() throws Exception {
        //given
        var token = "abc";
        var password = "123456";
        var resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setToken(token);
        resetPasswordDto.setNewPassword(password);
        resetPasswordDto.setRepeatNewPassword(password);
        var json = new ObjectMapper().writeValueAsString(resetPasswordDto);
        //when
        when(webAuthService.resetPassword(isA(ResetPasswordDto.class)))
                .thenThrow(PasswordDontMatchException.class);
        //then
        mockMvc.perform(
                post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void resetPasswordWhenSuccess_ThenReturnNoContentRequest() throws Exception {
        //given
        var token = "abc";
        var password = "123456";
        var resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setToken(token);
        resetPasswordDto.setNewPassword(password);
        resetPasswordDto.setRepeatNewPassword(password);
        var json = new ObjectMapper().writeValueAsString(resetPasswordDto);

        //when
        when(webAuthService.resetPassword(resetPasswordDto)).thenReturn(userDto);
        //then
        mockMvc.perform(
                post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void activateWhenTokenDtoIsNotValid_ThenThrowBadRequest() throws Exception {
        //given
        var token = "abc";
        var resetPasswordDto = new TokenDto();
        resetPasswordDto.setToken(token);
        var json = new ObjectMapper().writeValueAsString(resetPasswordDto);

        //when
        when(webAuthService.activate(isA(TokenDto.class))).thenThrow(UserTokenNotFoundException.class);
        //then
        mockMvc.perform(
                post("/api/auth/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void activateWhenUserTokenIsNotFound_ThenThrowNotFound() throws Exception {
        //given
        var token = "abc";
        var resetPasswordDto = new TokenDto();
        resetPasswordDto.setToken(token);
        var json = new ObjectMapper().writeValueAsString(resetPasswordDto);

        //when
        when(webAuthService.activate(isA(TokenDto.class))).thenThrow(UserTokenNotFoundException.class);
        //then
        mockMvc.perform(
                post("/api/auth/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void activateWhenUserNotFound_ThenThrowNotFound() throws Exception {
        //given
        var token = "abc";
        var resetPasswordDto = new TokenDto();
        resetPasswordDto.setToken(token);
        var json = new ObjectMapper().writeValueAsString(resetPasswordDto);

        //when
        when(webAuthService.activate(isA(TokenDto.class))).thenThrow(UserNotFoundException.class);
        //then
        mockMvc.perform(
                post("/api/auth/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void activateWhenSuccess_ThenReturnNoContent() throws Exception {
        //given
        var token = "abc";
        var resetPasswordDto = new TokenDto();
        resetPasswordDto.setToken(token);
        var json = new ObjectMapper().writeValueAsString(resetPasswordDto);

        //when
        when(webAuthService.activate(isA(TokenDto.class))).thenReturn(userDto);
        //then
        mockMvc.perform(
                post("/api/auth/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}