package com.dataworks.eventsubscriber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventRepositoryTest {
    @Autowired
    EventRepository eventRepository;

    @Test
    void eventRepositoryLoads() {
        assertThat(eventRepository).isNotNull();
        assertThat(eventRepository).isInstanceOf(EventRepository.class);
    }
}