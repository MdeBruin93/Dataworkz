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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class WebAuthServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    User userMock;
    @Mock
    RegisterDto registerDto;
    @InjectMocks
    WebAuthService webAuthService;

    @Test
    public void registerWhenUserIsFoundByGivenEmail_ThrowUserAlreadyExistException() {
        //arrange
        when(registerDto.getEmail()).thenReturn("ricky");
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(userMock);

        var foundEmail = userRepository.findByEmail("ricky");

        //act & assert
        assertThatExceptionOfType(UserAlreadyExistException.class)
            .isThrownBy(() -> {
                webAuthService.register(registerDto);
            });
    }
}