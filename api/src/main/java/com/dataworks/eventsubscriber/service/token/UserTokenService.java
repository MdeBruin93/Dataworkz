package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.mapper.UserTokenMapper;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.TokenDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public abstract class UserTokenService implements TokenService {
    private String email;
    private String token;
    @Setter(AccessLevel.PROTECTED)
    private TokenType tokenType;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserTokenMapper userTokenMapper;

    @Override
    public void verify() {
        if (getToken() == null || getEmail() == null || getTokenType() == null) {
            throw new NullPointerException();
        }

        var foundToken = userTokenRepository.findByTokenAndType(getToken(), tokenType)
                .orElseThrow(UserTokenNotFoundException::new);
        var isOwnerTokenEqualToGivenEmail = foundToken.getUser()
                .getEmail()
                .equals(getEmail());

        if (!isOwnerTokenEqualToGivenEmail) {
            throw new UserTokenNotFoundException();
        }

        foundToken.setTokenIsUsed(true);
        userTokenRepository.save(foundToken);
    }

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
