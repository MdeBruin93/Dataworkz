package com.dataworks.eventsubscriber.service.user;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dto.UserBlockDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAllByBlocked(false).stream()
                .map(userMapper::mapToDestination)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllBlocked() {
        return userRepository.findAllByBlocked(true).stream()
                .map(userMapper::mapToDestination)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        return userMapper.mapToDestination(user);
    }

    @Override
    public UserDto update(int id, UserBlockDto userBlockDto) {
        var foundUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        var isBlock = userBlockDto.isBlocked();
        var blockDescription = userBlockDto.getDescription();

        if (!isBlock) {
            blockDescription = null;
        }

        foundUser.setBlocked(isBlock);
        foundUser.setBlockedDescription(blockDescription);
        return userMapper.mapToDestination(userRepository.save(foundUser));
    }
}
