package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.exception.EmailSendFailedException;
import com.dataworks.eventsubscriber.exception.PasswordDontMatchException;
import com.dataworks.eventsubscriber.exception.user.UserAlreadyExistException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.RegisterMapper;
import com.dataworks.eventsubscriber.mapper.TokenMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.RegisterDto;
import com.dataworks.eventsubscriber.model.dto.ResetPasswordDto;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.model.dto.UserDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.service.UserTokenDecoder;
import com.dataworks.eventsubscriber.service.UserTokenService;
import com.dataworks.eventsubscriber.service.token.ActivateAccountTokenService;
import com.dataworks.eventsubscriber.service.token.ResetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class WebAuthService implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterMapper registerMapper;
    private final UserMapper userMapper;
    private final ActivateAccountTokenService activateAccountTokenService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final TokenMapper tokenMapper;

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
            activateAccountTokenService
                    .setEmail(registerDto.getEmail())
                    .generate();
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

    @Override
    public TokenDto forgotPassword(String email) {
        return resetPasswordTokenService.setEmail(email).generate();
    }

    @Override
    public UserDto resetPassword(ResetPasswordDto resetPasswordDto) {
        var tokenDto = tokenMapper.mapResetPasswordDtoToSource(resetPasswordDto);
        resetPasswordTokenService
                .setTokenDto(tokenDto)
                .verify();

        var email = resetPasswordTokenService.getEmail();
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(user);

        return userMapper.mapToDestination(user);
    }

    @Override
    public UserDto activate(TokenDto tokenDto) {
        activateAccountTokenService.setTokenDto(tokenDto).verify();

        var email = activateAccountTokenService.getEmail();
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setEmailVerified(true);

        return userMapper.mapToDestination(userRepository.save(user));
    }
}