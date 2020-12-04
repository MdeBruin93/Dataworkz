package com.dataworks.eventsubscriber.service.auth;

import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import com.dataworks.eventsubscriber.service.UserTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserTokenServiceTests {

    @Mock
    UserTokenRepository userTokenRepository;

    @Mock
    UserTokenService userTokenService;

    @Test
    public void createNewEmailTokenForUser_ShouldSucceed() {
        // Arrange
        var email = "0947704@hr.nl";

        // Act
        var token = userTokenService.createEmailTokenForUser(email);

        // Assert
        assertThat(token.getToken()).isNotEmpty();
    }
}
