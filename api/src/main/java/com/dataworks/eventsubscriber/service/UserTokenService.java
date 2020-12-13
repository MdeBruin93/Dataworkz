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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTokenService implements TokenService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final EmailProvider emailProvider;

    @Value("${spring.mail.host}")
    private String host;

    @Override
    public void createEmailTokenForUser(String email) {
        var token = createTokenForUser(email, TokenType.EmailConfirmation);
        var content = String.format("<a href=" + host + "api/usertokens/verifyuseremail/%s>Verify your email</a>", token);

        emailProvider.send(email, "Verify your email", content, true);
    }

    @Override
    public void createPasswordResetTokenForUser(String email) {
        var token = createTokenForUser(email, TokenType.PasswordReset);
        var content = String.format("<a href=" +host + "api/usertokens/verifypasswordreset/%s>Reset your password</a>", token);

        emailProvider.send(email, "Reset your password", content, true);
    }

    @Override
    public boolean verifyTokenForUser(String token) {
        var decrypted = new String(Base64.getDecoder().decode(token.getBytes()));
        var splitted = decrypted.split("\\:");
        var user = userRepository.findByEmail(splitted[0]).orElseThrow(() -> new UserNotFoundException(splitted[0]));
        var userToken = userTokenRepository.findByToken(splitted[1]).orElseThrow(UserTokenNotFoundException::new);

        if (splitted[2].equals(TokenType.EmailConfirmation.toString())) {
            user.setEmailVerified(true);
            userRepository.save(user);
        }
        userToken.setTokenIsUsed(true);
        userToken.setModified(LocalDateTime.now());
        userTokenRepository.save(userToken);

        return true;
    }

    private String createTokenForUser(String email, TokenType tokenType) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        var token = UUID.randomUUID().toString();
        var encryptedToken = new String(Base64.getEncoder().encode((email + ":" + token + ":" + tokenType.toString()).getBytes()));
        var userToken = new UserToken();
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setType(tokenType);
        userTokenRepository.save(userToken);

        return encryptedToken;
    }
}
