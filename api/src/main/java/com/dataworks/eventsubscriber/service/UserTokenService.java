package com.dataworks.eventsubscriber.service;

import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.model.dto.ForgotPasswordDto;
import com.dataworks.eventsubscriber.repository.UserRepository;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import com.dataworks.eventsubscriber.service.email.EmailProvider;
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

        emailProvider.setEmail(email)
                .setSubject("Verify your email")
                .setContent(content)
                .send();
    }

    @Override
    public String createPasswordResetTokenForUser(String email) {
        var token = createTokenForUser(email, TokenType.PasswordReset);
        var content = String.format("<a href=" +host + "Auth/reset-password/%s>Reset your password</a>", token);

        emailProvider.setEmail(email)
                .setSubject("Reset your password")
                .setContent(content)
                .send();

        return token;
    }

    @Override
    public boolean verifyTokenForUser(String token) {
        var decoded = new UserTokenDecoder(token).getDecodedToken();
        var user = userRepository.findByEmail(decoded.getEmail()).orElseThrow(() -> new UserNotFoundException(decoded.getEmail()));
        var userToken = userTokenRepository.findByToken(decoded.getToken()).orElseThrow(UserTokenNotFoundException::new);

        if (decoded.getTokenType() == TokenType.EmailConfirmation) {
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

    @Override
    public void generatePasswordResetToken(ForgotPasswordDto forgotPasswordDto) {
        var email = forgotPasswordDto.getEmail();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));


    }
}
