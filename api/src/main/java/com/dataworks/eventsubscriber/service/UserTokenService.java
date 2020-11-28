package com.dataworks.eventsubscriber.service;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dao.TokenType;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.UserTokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserTokenService implements TokenService {

    UserRepository userRepository;
    UserTokenRepository userTokenRepository;
    UserTokenMapper userTokenMapper;

    @Override
    public UserTokenDto createEmailTokenForUser(String email) {
        var user = userRepository.findByEmail(email);

        var token = email + ".email";
        var userToken = new UserToken();
        userToken.setUserId(user.getId());
        userToken.setToken(token);
        userToken.setType(TokenType.EmailConfirmation);
        userTokenRepository.save(userToken);
        return userTokenMapper.mapToDestination(userToken);
    }

    @Override
    public boolean verifyEmailTokenForUser(String token) {
        var splitted = token.split("\\.");
        var user = userRepository.findByEmail(splitted[0]);

        if (user == null) {
            throw new UserNotFoundException(splitted[0]);
        }
        var foundToken = userTokenRepository.findByToken(splitted[1], TokenType.EmailConfirmation);
        if (foundToken == null) {
             throw new UserTokenNotFoundException();
        }
        return true;
    }
}
