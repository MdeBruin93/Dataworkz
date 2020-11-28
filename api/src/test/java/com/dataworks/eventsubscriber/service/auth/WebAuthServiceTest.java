package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.mapper.RegisterMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    UserRepository userRepository;
    @Mock
    RegisterMapper registerMapper;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    WebAuthService webAuthService;

    @Test
    public void registerWhenUserIsFoundByGivenEmail_ThrowUserAlreadyExistException() {
        //arrange
        var email = "ricky@hr.nl";
        when(registerDto.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //act & assert
        assertThatExceptionOfType(UserAlreadyExistException.class)
            .isThrownBy(() -> {
                webAuthService.register(registerDto);
            });
    }

    @Test
    public void registerWhenUserIsNotFoundByGivenEmail_Save() {
        //arrange
        var email = "ricky@hr.nl";
        when(registerDto.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(registerMapper.mapToUserSource(registerDto)).thenReturn(user);
        when(userMapper.mapToDestination(user)).thenReturn(userDto);
        when(userRepository.save(user)).thenReturn(user);

        //act
        var result = webAuthService.register(registerDto);

        //assert
        assertThat(result).isInstanceOf(UserDto.class);
        verify(userRepository, times(1)).findByEmail(email);
        verify(registerMapper, times(1)).mapToUserSource(registerDto);
        verify(userRepository, times(1)).save(user);
    }
}