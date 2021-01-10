package com.dataworks.eventsubscriber.service.wishlist;

import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.WishlistRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistImplServiceTest {
    @Mock
    AuthService authService;
    @Mock
    WishlistRepository wishlistRepository;
    @Mock
    EventRepository eventRepository;
    @Mock
    AuthService authService;
    @InjectMocks
    WishlistImplService wishlistImplService;
    @Test
    void findByUserIdWhenUserDontHaveWishlists_ThenReturnEmptyList() {
        //given
        var loggedInUser = new User();
        //when
        when()
        //then
    }

    @Test
    void findByUserIdWhenUserHaveWishlists_ThenReturnList() {
        //given
//        var loggedInUser/
        //when
        //then
    }

    @Test
    void findByIdAndUserId() {
    }

    @Test
    void store() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}