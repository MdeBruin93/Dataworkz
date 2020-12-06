package com.dataworks.eventsubscriber.repositories;

import com.dataworks.eventsubscriber.repository.UserTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTokenRepositoryTests {
    @MockBean
    Authentication authentication;
    @Autowired
    UserTokenRepository userTokenRepository;

    @Test
    public void loadUserTokenRepository() {
        assertThat(userTokenRepository).isNotNull();
        assertThat(userTokenRepository).isInstanceOf(UserTokenRepository.class);
    }
}