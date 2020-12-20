package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.mapper.RegisterMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.model.dto.UserTokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.service.UserTokenService;
import com.dataworks.eventsubscriber.service.token.ActivateAccountTokenService;
import com.dataworks.eventsubscriber.service.token.ResetPasswordTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebAuthServiceTest {

    @Mock
    User user;
    @Mock
    UserDto userDto;
    @Mock
    RegisterDto registerDto;
    @Mock
    UserTokenDto userTokenDto;
    @Mock
    UserRepository userRepository;
    @Mock
    RegisterMapper registerMapper;
    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserTokenService userTokenService;
    @Mock
    ResetPasswordTokenService resetPasswordTokenService;
    @Mock
    ActivateAccountTokenService activateAccountTokenService;
    @Mock
    TokenDto tokenDto;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @InjectMocks
    WebAuthService webAuthService;

    @Test
    public void registerWhenUserIsFoundByGivenEmail_ThrowUserAlreadyExistException() {
        //given
        var email = "ricky@hr.nl";

        //when
        when(registerDto.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //then
        assertThatExceptionOfType(UserAlreadyExistException.class)
            .isThrownBy(() -> {
                webAuthService.register(registerDto);
            });
    }

    @Test
    public void registerWhenUserIsNotFoundByGivenEmail_Save() {
        //given
        var email = "ricky@hr.nl";

        //when
        when(registerDto.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(registerMapper.mapToUserSource(registerDto)).thenReturn(user);
        when(userMapper.mapToDestination(user)).thenReturn(userDto);
        when(userRepository.save(user)).thenReturn(user);
        doNothing().when(userTokenService).createEmailTokenForUser(email);

        //then
        var result = webAuthService.register(registerDto);
        assertThat(result).isInstanceOf(UserDto.class);
        verify(userRepository, times(1)).findByEmail(email);
        verify(registerMapper, times(1)).mapToUserSource(registerDto);
        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    void forgotPassword() {
        //given
        var email = "info@hr.nl";
        //when
        when(resetPasswordTokenService.setEmail(email)).thenReturn(resetPasswordTokenService);
        when(resetPasswordTokenService.generate()).thenReturn(tokenDto);
        //then
        var result = webAuthService.forgotPassword(email);
        assertThat(result).isInstanceOf(TokenDto.class);
    }

    @Test
    void activate() {
        //given
        var email = "info@hr.nl";
        //when
        when(activateAccountTokenService.setTokenDto(tokenDto)).thenReturn(activateAccountTokenService);
        when(activateAccountTokenService.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToDestination(user)).thenReturn(userDto);
        //then
        var result = webAuthService.activate(tokenDto);
        assertThat(result).isInstanceOf(UserDto.class);
    }
}
