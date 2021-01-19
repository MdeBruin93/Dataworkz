package com.dataworks.eventsubscriber.service.user;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.UserBlockDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void findAllWhenNoUsersFound_ThenReturnEmptyList() {
        //given
        var expectedList = new ArrayList<UserDto>();
        //when
        when(userRepository.findAllByBlocked(false)).thenReturn(new ArrayList<User>());
        //then
        var result = userService.findAll();
        assertThat(result).isEqualTo(expectedList);
        assertThat(result.isEmpty()).isTrue();
        verify(userRepository, times(1)).findAllByBlocked(false);
        verify(userMapper, times(0)).mapToDestination(any(User.class));
    }

    @Test
    void findAllWhenUsersFound_ThenReturnList() {
        //given
        var user = new User();
        var returnList = new ArrayList<User>();
        returnList.add(user);
        var expectedDto = new UserDto();

        //when
        when(userRepository.findAllByBlocked(false)).thenReturn(returnList);
        when(userMapper.mapToDestination(user)).thenReturn(expectedDto);
        //then
        var result = userService.findAll();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0)).isEqualTo(expectedDto);
        verify(userRepository, times(1)).findAllByBlocked(false);
        verify(userMapper, times(1)).mapToDestination(any(User.class));
    }

    @Test
    void findAllBlockedWhenNoUsersFound_ThenReturnEmptyList() {
        //given
        var expectedList = new ArrayList<UserDto>();
        //when
        when(userRepository.findAllByBlocked(true)).thenReturn(new ArrayList<User>());
        //then
        var result = userService.findAllBlocked();
        assertThat(result).isEqualTo(expectedList);
        assertThat(result.isEmpty()).isTrue();
        verify(userRepository, times(1)).findAllByBlocked(true);
        verify(userMapper, times(0)).mapToDestination(any(User.class));
    }

    @Test
    void findAllBlockedWhenUsersFound_ThenReturnEmptyList() {
        //given
        var user = new User();
        var returnList = new ArrayList<User>();
        returnList.add(user);
        var expectedDto = new UserDto();

        //when
        when(userRepository.findAllByBlocked(true)).thenReturn(returnList);
        when(userMapper.mapToDestination(user)).thenReturn(expectedDto);
        //then
        var result = userService.findAllBlocked();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0)).isEqualTo(expectedDto);
        verify(userRepository, times(1)).findAllByBlocked(true);;
        verify(userMapper, times(1)).mapToDestination(any(User.class));
    }

    @Test
    void updateWhenUserNotFound_ThenThrowException() {
        //given
        var userId = 1;
        var foundUser = new User();
        var userBlockDto = new UserBlockDto();
        //when
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.update(userId, userBlockDto));
    }

    @Test
    void updateWhenUserIsBlocked_ThenDescriptionIsSet() {
        //given
        var foundUser = new User();
        foundUser.setId(1);
        var userBlockDto = new UserBlockDto();
        userBlockDto.setBlocked(true);
        userBlockDto.setDescription("Yes nice description");
        var userDto = new UserDto();

        //when
        when(userRepository.findById(foundUser.getId())).thenReturn(Optional.of(foundUser));
        when(userRepository.save(foundUser)).thenReturn(foundUser);
        when(userMapper.mapToDestination(foundUser)).thenReturn(userDto);

        //then
        var result = userService.update(foundUser.getId(), userBlockDto);
        assertThat(result).isEqualTo(userDto);
        assertThat(foundUser.getBlockedDescription()).isEqualTo("Yes nice description");
        verify(userRepository, times(1)).findById(foundUser.getId());
        verify(userRepository, times(1)).save(foundUser);
        verify(userMapper, times(1)).mapToDestination(foundUser);
    }



    @Test
    void updateWhenUserIsNotBlocked_ThenDescriptionIsSet() {
        //given
        var foundUser = new User();
        foundUser.setId(1);
        var userBlockDto = new UserBlockDto();
        userBlockDto.setBlocked(false);
        userBlockDto.setDescription("Yes nice description");
        var userDto = new UserDto();

        //when
        when(userRepository.findById(foundUser.getId())).thenReturn(Optional.of(foundUser));
        when(userRepository.save(foundUser)).thenReturn(foundUser);
        when(userMapper.mapToDestination(foundUser)).thenReturn(userDto);

        //then
        var result = userService.update(foundUser.getId(), userBlockDto);
        assertThat(result).isEqualTo(userDto);
        assertThat(foundUser.isBlocked()).isFalse();
        assertThat(foundUser.getBlockedDescription()).isNull();
        verify(userRepository, times(1)).findById(foundUser.getId());
        verify(userRepository, times(1)).save(foundUser);
        verify(userMapper, times(1)).mapToDestination(foundUser);
    }
}