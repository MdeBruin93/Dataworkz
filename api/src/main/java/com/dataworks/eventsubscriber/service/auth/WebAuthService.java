package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.exception.EmailSendFailedException;
import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.RegisterMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WebAuthService implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterMapper registerMapper;
    private final UserMapper userMapper;
    private final UserTokenService userTokenService;

    @Override
    public UserDto register(RegisterDto registerDto) {
        var email = registerDto.getEmail();
        var foundUser = this.userRepository.findByEmail(email);

        if (foundUser.isPresent()) {
            throw new UserAlreadyExistException();
        }

        var mappedUser = registerMapper.mapToUserSource(registerDto);
        mappedUser.setPassword(this.passwordEncoder.encode(mappedUser.getPassword()));
        mappedUser.setRole("ROLE_USER");

        var savedUser = userRepository.save(mappedUser);

        try {
            userTokenService.createEmailTokenForUser(registerDto.getEmail());
        } catch (EmailSendFailedException esfe) {
            System.out.println(esfe.getMessage());
            userRepository.delete(savedUser);
        }

        return userMapper.mapToDestination(savedUser);
    }

    @Override
    public UserDto my() {
        var foundResult = this.myDao();

        return userMapper.mapToDestination(foundResult);
    }

    @Override
    public User myDao() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var isAuthenticated = authentication.isAuthenticated();

        if (!isAuthenticated) {
            return null;
        }

        return userRepository.findByEmail(authentication.getName()).get();
    }

    @Override
    public User myDaoOrFail() {
        var dao = this.myDao();

        if (dao == null) {
            throw new UserNotFoundException();
        }

        return dao;
    }
}