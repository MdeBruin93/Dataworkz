package com.dataworks.eventsubscriber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WishlistRepositoryTests {
    @Autowired
    WishlistRepository wishlistRepository;

    @Test
    void wishlistRepositoryLoads() {
        assertThat(wishlistRepository).isNotNull();
        assertThat(wishlistRepository).isInstanceOf(WishlistRepository.class);
    }
}