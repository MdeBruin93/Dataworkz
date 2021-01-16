package com.dataworks.eventsubscriber.service.user;

import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    List<UserDto> findAllBlocked();
    UserDto block(int id);
    UserDto unblock(int id);
}
