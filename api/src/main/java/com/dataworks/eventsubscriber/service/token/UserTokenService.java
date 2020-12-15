package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public abstract class UserTokenService implements TokenService {
    @Setter
    @Getter
    private String email;
    @Setter(AccessLevel.PROTECTED)
    @Getter
    private TokenType tokenType;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserTokenMapper userTokenMapper;

    @Override
    public TokenDto generate() {
        if (this.getEmail() == null || this.getTokenType() == null) {
            throw new NullPointerException();
        }

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        var token = UUID.randomUUID().toString();

        var userToken = new UserToken();
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setType(tokenType);

        return userTokenMapper.mapToDestination(userTokenRepository.save(userToken));
    }
}
