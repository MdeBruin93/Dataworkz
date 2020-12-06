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
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTokenService implements TokenService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserTokenMapper userTokenMapper;
    private final EmailProvider emailProvider;

    @Override
    public UserTokenDto createEmailTokenForUser(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        var token = UUID.randomUUID().toString();
        var encryptedToken = new String(Base64.getEncoder().encode((email + ":" + token).getBytes()));
        var userToken = new UserToken();
        userToken.setUserId(user.getId());
        userToken.setToken(token);
        userToken.setType(TokenType.EmailConfirmation);
        userTokenRepository.save(userToken);

        emailProvider.send(email, encryptedToken);
        return userTokenMapper.mapToDestination(userToken);
    }

    @Override
    public boolean verifyEmailTokenForUser(String token) {
        var decrypted = new String(Base64.getDecoder().decode(token.getBytes()));
        var splitted = decrypted.split("\\:");
        var user = userRepository.findByEmail(splitted[0]).orElseThrow(() -> new UserNotFoundException(splitted[0]));
        userTokenRepository.findByToken(splitted[1]).orElseThrow(UserTokenNotFoundException::new);

        user.setEmailVerified(true);
        userRepository.save(user);
        return true;
    }
}
