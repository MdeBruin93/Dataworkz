package com.dataworks.eventsubscriber.service.wishlist;

import com.dataworks.eventsubscriber.enums.Role;
import com.dataworks.eventsubscriber.exception.WishlistNotFoundException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.mapper.WishListMapper;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dao.Wishlist;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.WishlistRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WishlistImplServiceTests {
    @Mock
    User user;
    @Mock
    Wishlist wishlist;
    @Mock
    WishlistDto wishlistDto;
    @Mock
    List<Wishlist> wishlists;
    @Mock
    List<Event> events;
    @Mock
    AuthService authService;
    @Mock
    WishListMapper wishListMapper;
    @Mock
    WishlistRepository wishlistRepository;
    @Mock
    List<WishlistDto> wishlistDtos;
    @Mock
    EventRepository eventRepository;
    @Mock
    WebAuthDetailService webAuthDetailService;
    @InjectMocks
    WishlistServiceImpl wishlistService;

    @Test
    public void findByUserId_ShouldThrowUserIsNotLoggedInException() {
        // given

        // when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);

        // then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> wishlistService.findByUserId());
        verify(authService, times(1)).myDaoOrFail();
    }

    @Test
    public void findByUserId_ShouldFindTheWishlistFromTheLoggedInUser() {
        //given
        User loggedInUser = user;

        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishlistRepository.findByUserId(loggedInUser.getId())).thenReturn(wishlists);
        when(wishListMapper.mapToEventDestinationCollection(wishlists)).thenReturn(wishlistDtos);

        // when
        var wishlistDtos = wishlistService.findByUserId();

        // then
        assertThat(wishlistDtos).hasSize(0);
        verify(wishlistRepository, times(1)).findByUserId(loggedInUser.getId());
        verify(wishListMapper, times(1)).mapToEventDestinationCollection(wishlists);
    }

    @Test
    public void findByIdAndUserId_ShouldThrowUserIsNotLoggedInException() {
        // given

        // when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);

        // then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> wishlistService.findByUserId());
        verify(authService, times(1)).myDaoOrFail();
    }

    @Test
    public void findByIdAndUserId_ShouldFindTheWishlistFromTheLoggedInUser() {
        //given
        User loggedInUser = user;
        var wishListId = 1;
        var wishlist = new Wishlist();
        wishlist.setId(wishListId);

        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishlistRepository.findByIdAndUserId(wishlist.getId(), loggedInUser.getId())).thenReturn(Optional.of(wishlist));
        when(wishListMapper.mapToEventDestination(wishlist)).thenReturn(wishlistDto);

        // when
        var wishlistDto = wishlistService.findByIdAndUserId(wishListId);

        // then
        assertThat(wishlistDto).isNotNull();
        verify(wishlistRepository, times(1)).findByIdAndUserId(wishListId, loggedInUser.getId());
        verify(wishListMapper, times(1)).mapToEventDestination(wishlist);
    }

    @Test
    public void store_ShouldThrowUserIsNotLoggedInException() {
        // given

        // when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);

        // then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> wishlistService.store(new WishlistDto()));
        verify(authService, times(1)).myDaoOrFail();
    }

    @Test
    public void store_ShouldSaveANewWishlistWithoutEvents() {
        //given
        User loggedInUser = user;
        WishlistDto savedWishlistDto = wishlistDto;

        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishListMapper.mapToEventSource(wishlistDto)).thenReturn(wishlist);
        when(wishListMapper.mapToEventDestination(wishlist)).thenReturn(savedWishlistDto);
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        // when
        savedWishlistDto = wishlistService.store(wishlistDto);

        // then
        assertThat(savedWishlistDto).isNotNull();
        verify(authService, times(1)).myDaoOrFail();
        verify(wishListMapper, times(1)).mapToEventSource(wishlistDto);
        verify(wishListMapper, times(1)).mapToEventDestination(wishlist);
        verify(wishlistRepository, times(1)).save(wishlist);
    }

    @Test
    public void store_ShouldSaveANewWishlistWithEvents() {
        //given
        User loggedInUser = user;
        var eventId = 1;
        var eventIds = new ArrayList<Integer>();
        eventIds.add(eventId);
        var wishlistDto = new WishlistDto();
        var savedWishlistDto = new WishlistDto();

        wishlistDto.setEventIds(eventIds);

        var event = new Event();
        event.setId(eventId);

        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishListMapper.mapToEventSource(wishlistDto)).thenReturn(wishlist);
        when(wishListMapper.mapToEventDestination(wishlist)).thenReturn(savedWishlistDto);
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // when
        savedWishlistDto = wishlistService.store(wishlistDto);

        // then
        assertThat(savedWishlistDto).isNotNull();
        verify(authService, times(1)).myDaoOrFail();
        verify(wishListMapper, times(1)).mapToEventSource(wishlistDto);
        verify(wishListMapper, times(1)).mapToEventDestination(wishlist);
        verify(wishlistRepository, times(1)).save(wishlist);
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    public void update_ShouldThrowUserIsNotLoggedInException() {
        // given
        var wishlistId = 1;

        // when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);

        // then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> wishlistService.update(wishlistId, new WishlistDto()));
        verify(authService, times(1)).myDaoOrFail();
    }

    @Test
    public void update_ShouldSaveANewWishlistWithoutEvents() {
        //given
        var userId = 1;
        var wishlistId = 1;
        User loggedInUser = new User();
        WishlistDto savedWishlistDto = new WishlistDto();
        var wishlist = new Wishlist();
        var wishlistDto = new WishlistDto();
        wishlist.setEvents(new ArrayList<>());

        wishlist.setUser(loggedInUser);
        loggedInUser.setId(userId);

        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishListMapper.mapToEventDestination(wishlist)).thenReturn(savedWishlistDto);
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // when
        savedWishlistDto = wishlistService.update(wishlistId, wishlistDto);

        // then
        assertThat(savedWishlistDto).isNotNull();
        verify(authService, times(1)).myDaoOrFail();
        verify(wishListMapper, times(1)).mapToEventDestination(wishlist);
        verify(wishlistRepository, times(1)).save(wishlist);
        verify(wishlistRepository, times(1)).findById(wishlistId);
    }

    @Test
    public void update_ShouldSaveANewWishlistWithEvents() {
        //given
        var userId = 1;
        var loggedInUser = new User();
        var eventId = 1;
        var wishlistId = 1;
        var wishlist = new Wishlist();
        var wishlistDto = new WishlistDto();
        var savedWishlistDto = new WishlistDto();
        var eventIds = new ArrayList<Integer>();
        var event = new Event();

        eventIds.add(eventId);
        wishlist.setUser(loggedInUser);
        wishlist.setEvents(new ArrayList<>());
        wishlistDto.setEventIds(eventIds);
        event.setId(eventId);
        loggedInUser.setId(userId);

        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishListMapper.mapToEventDestination(wishlist)).thenReturn(savedWishlistDto);
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // when
        savedWishlistDto = wishlistService.update(wishlistId, wishlistDto);

        // then
        assertThat(savedWishlistDto).isNotNull();
        verify(authService, times(1)).myDaoOrFail();
        verify(wishListMapper, times(1)).mapToEventDestination(wishlist);
        verify(wishlistRepository, times(1)).save(wishlist);
        verify(eventRepository, times(1)).findById(eventId);
        verify(wishlistRepository, times(1)).findById(wishlistId);
    }

    @Test
    public void delete_ShouldThrowUserIsNotLoggedInException() {
        // given
        var wishlistId = 1;

        // when
        when(authService.myDaoOrFail()).thenThrow(UserNotFoundException.class);

        // then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> wishlistService.delete(wishlistId));
        verify(authService, times(1)).myDaoOrFail();
    }

    @Test
    public void delete_ShouldThrowUserIsNotOwner() {
        // given
        var wishlistId = 1;
        var userId = 1;
        User loggedInUser = new User();
        var wishlist = new Wishlist();
        var randomUser = new User();

        loggedInUser.setRole(Role.ROLE_USER.toString());
        randomUser.setId(2);
        loggedInUser.setId(userId);
        wishlist.setUser(randomUser);

        // when
        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // then
        assertThatExceptionOfType(WishlistNotFoundException.class)
                .isThrownBy(() -> wishlistService.delete(wishlistId));

        verify(authService, times(1)).myDaoOrFail();
        verify(wishlistRepository, times(1)).findById(wishlistId);
    }

    @Test
    public void delete_ShouldDeleteWishlistBecauseUserIsOwner() {
        // given
        var wishlistId = 1;
        var userId = 1;
        User loggedInUser = new User();
        var wishlist = new Wishlist();
        var randomUser = new User();

        loggedInUser.setRole(Role.ROLE_USER.toString());
        randomUser.setId(wishlistId);
        loggedInUser.setId(userId);
        wishlist.setUser(randomUser);

        // when
        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // then
        wishlistService.delete(wishlistId);

        verify(authService, times(1)).myDaoOrFail();
        verify(wishlistRepository, times(1)).findById(wishlistId);
    }

    @Test
    public void delete_ShouldDeleteBecauseUserIsAdmin() {
        // given
        var wishlistId = 1;
        var userId = 1;
        User loggedInUser = new User();
        var wishlist = new Wishlist();
        var randomUser = new User();

        loggedInUser.setRole(Role.ROLE_ADMIN.toString());
        randomUser.setId(2);
        loggedInUser.setId(userId);
        wishlist.setUser(randomUser);

        // when
        when(authService.myDaoOrFail()).thenReturn(loggedInUser);
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        // then
        wishlistService.delete(wishlistId);

        verify(authService, times(1)).myDaoOrFail();
        verify(wishlistRepository, times(1)).findById(wishlistId);
    }
}