package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.mapper.RegisterMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WebAuthService implements AuthService {
    private final UserRepository userRepository;
    private final RegisterMapper registerMapper;
    private final UserMapper userMapper;

    @Override
    public UserDto register(RegisterDto registerDto) {
        var email = registerDto.getEmail();
        var foundUser = this.userRepository.findByEmail(email);

        if (foundUser.isPresent()) {
            throw new UserAlreadyExistException();
        }

        var mappedUser = registerMapper.mapToUserSource(registerDto);
        mappedUser.setRole("ROLE_USER");
        //@todo hash password when spring security is installed

        var savedUser = userRepository.save(mappedUser);

        return userMapper.mapToDestination(savedUser);
    }
}
