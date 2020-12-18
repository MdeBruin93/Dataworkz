package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import com.dataworks.eventsubscriber.service.email.EmailProvider;
import com.dataworks.eventsubscriber.service.storage.LocalStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResetPasswordTokenServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserTokenRepository userTokenRepository;
    @Mock
    UserTokenMapper userTokenMapper;
    @Mock
    EmailProvider emailProvider;
    @Mock
    User user;
    @Mock
    UserToken userToken;
    @Mock
    TokenDto tokenDto;
    @InjectMocks
    ResetPasswordTokenService resetPasswordTokenService;

    @Test
    void verifyWhenTokenAndEmailAndTokenTypeIsNotSet_ThenThrowException() {
        //given
        resetPasswordTokenService.setTokenType(null);
        //when
        //then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> resetPasswordTokenService.verify());
    }

    @Test
    void verifyWhenTokenIsSetAndEmailAndTokenTypeIsNotSet_ThenThrowException() {
        //given
        resetPasswordTokenService.setTokenType(null);
        resetPasswordTokenService.setTokenDto(tokenDto);
        //when
        //then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> resetPasswordTokenService.verify());
    }

    @Test
    void verifyWhenTokenNotSetAndEmailIsSetAndTokenTypeIsNotSet_ThenThrowException() {
        //given
        resetPasswordTokenService.setTokenType(null);
        resetPasswordTokenService.setEmail("ricky@hr.nl");
        //when
        //then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> resetPasswordTokenService.verify());
    }

    @Test
    void verifyWhenTokenNotSetAndEmailIsNotAndTokenTypeIsSet_ThenThrowException() {
        //given
        //when
        //then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> resetPasswordTokenService.verify());
    }

    @Test
    void verifyWhenTokenIsNotFoundBeTokenAndType_ThenThrowException() {
        //given
        resetPasswordTokenService.setTokenDto(tokenDto);
        resetPasswordTokenService.setEmail("info@hr.nl");
        var token = "abc";

        //when
        when(tokenDto.getToken()).thenReturn(token);
        when(userTokenRepository.findByTokenAndTypeAndTokenIsUsed(anyString(), any(TokenType.class), false))
                .thenReturn(Optional.empty());
        //then
        assertThatExceptionOfType(UserTokenNotFoundException.class)
                .isThrownBy(() -> resetPasswordTokenService.verify());
    }

    @Test
    void verifyWhenTokenIsFoundAndOwnerEmailIsEqualGivenEmail_ThenVerify() {
        //given
        resetPasswordTokenService.setTokenDto(tokenDto);
        resetPasswordTokenService.setEmail("info@hr.nl");
        var token = "aW5mb0Boci5ubDphYmM=";

        //when
        when(tokenDto.getToken()).thenReturn(token);
        when(userTokenRepository.findByTokenAndTypeAndTokenIsUsed(anyString(), any(TokenType.class), false))
                .thenReturn(Optional.of(userToken));
        when(userToken.getUser()).thenReturn(user);
        when(user.getEmail()).thenReturn("info@hr.nl");
        //then
        resetPasswordTokenService.verify();
        verify(userTokenRepository, times(1)).save(userToken);
    }

    @Test
    void generateWhenEmailAndTypeIsNotSet_ThenThrowException() {
        //given
        //when
        //then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> resetPasswordTokenService.generate());
    }

    @Test
    void generateWhenEmailIsSetAndTypeIsNotSet_ThenThrowException() {
        //given
        var email = "ricky@hr.nl";
        resetPasswordTokenService.setEmail(email);
        resetPasswordTokenService.setTokenType(null);

        //when

        // then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> resetPasswordTokenService.generate());
    }

    @Test
    void generateWhenEmailIsNotSetAndFileIsSet_ThenThrowException() {
        //given

        //when

        // then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> resetPasswordTokenService.generate());
    }

    @Test
    void generateWhenUserIsNotFoundByEmail_ThenThrowException() {
        //given
        var email = "ricky@hr.nl";
        resetPasswordTokenService.setEmail(email);

        //when
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> resetPasswordTokenService.generate());
    }

    @Test
    void generateWhenUserFound_ThenSave() {
        //given
        var email = "ricky@hr.nl";
        resetPasswordTokenService.setEmail(email);

        //when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userTokenRepository.save(any(UserToken.class))).thenReturn(userToken);
        when(emailProvider.setEmail(anyString())).thenReturn(emailProvider);
        when(emailProvider.setSubject(anyString())).thenReturn(emailProvider);
        when(emailProvider.setContent(anyString())).thenReturn(emailProvider);
        when(userTokenMapper.mapToDestination(any(UserToken.class))).thenReturn(tokenDto);

        // then
        var result = resetPasswordTokenService.generate();
        assertThat(result).isInstanceOf(TokenDto.class);
    }
}