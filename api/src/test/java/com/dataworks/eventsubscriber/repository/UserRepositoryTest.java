package com.dataworks.eventsubscriber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {
    @MockBean
    Authentication authentication;
    @Autowired
    UserRepository userRepository;

    @Test
    void userRepositoryLoads() {
        assertThat(userRepository).isNotNull();
        assertThat(userRepository).isInstanceOf(UserRepository.class);
    }
}