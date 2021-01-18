package com.dataworks.eventsubscriber.service.user;

import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.UserBlockDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    List<UserDto> findAllBlocked();
    UserDto update(int id, UserBlockDto userBlockDto);
}
