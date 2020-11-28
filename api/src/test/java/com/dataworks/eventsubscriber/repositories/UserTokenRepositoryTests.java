package com.dataworks.eventsubscriber.repositories;

import com.dataworks.eventsubscriber.exception.user.UserTokenNotFoundException;
import com.dataworks.eventsubscriber.model.dao.TokenType;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class UserTokenRepositoryTests {

    @Mock
    UserTokenRepository userTokenRepository;

    @Mock
    UserToken userToken;

    @Test
    public void registerNewEmailVerificationToken_ShouldSucceed() {
        // Arrange
        var token = "EmailToken";
        userToken.setToken(token);
        userToken.setType(TokenType.EmailConfirmation);

        // Act
        userTokenRepository.save(userToken);

        // Assert
        var found = userTokenRepository.findByToken(token, TokenType.EmailConfirmation);
        Assert.hasText(found.getToken(), token);
    }
}