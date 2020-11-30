package com.dataworks.eventsubscriber;

import com.dataworks.eventsubscriber.service.auth.WebAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

@SpringBootTest
class EventSubscriberApplicationTests {
    @MockBean
    Authentication authentication;

    @Test
    void contextLoads() {
    }
}
