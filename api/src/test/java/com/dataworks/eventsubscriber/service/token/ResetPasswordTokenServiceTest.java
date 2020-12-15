package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
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
    User user;
    @Mock
    UserToken userToken;
    @Mock
    TokenDto tokenDto;
    @InjectMocks
    ResetPasswordTokenService resetPasswordTokenService;

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
        when(userTokenMapper.mapToDestination(any(UserToken.class))).thenReturn(tokenDto);

        // then
        var result = resetPasswordTokenService.generate();
        assertThat(result).isInstanceOf(TokenDto.class);
    }
}