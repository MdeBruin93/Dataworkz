package com.dataworks.eventsubscriber.service.wishlist;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.exception.WishlistNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.mapper.WishListMapper;
import com.dataworks.eventsubscriber.model.dao.Wishlist;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.repository.WishlistRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {

    private final AuthService authService;
    private final WishlistRepository wishlistRepository;
    private final EventRepository eventRepository;
    private final WishListMapper wishListMapper;

    @Override
    public List<WishlistDto> findByUserId() {
        var loggedInUser = authService.myDaoOrFail();
        var wishlists = wishlistRepository.findByUserId(loggedInUser.getId());
        return wishListMapper.mapToEventDestinationCollection(wishlists);
    }

    @Override
    public WishlistDto findByIdAndUserId(int id) {
        var loggedInUser = authService.myDaoOrFail();
        var wishlist = wishlistRepository.findByIdAndUserId(id, loggedInUser.getId()).orElseThrow(WishlistNotFoundException::new);
        return wishListMapper.mapToEventDestination(wishlist);
    }

    @Override
    public WishlistDto store(WishlistDto wishlistDto) {
        var loggedInUser = authService.myDaoOrFail();

        var wishlist = wishListMapper.mapToEventSource(wishlistDto);
        wishlist.setUser(loggedInUser);
        addEventsToWishlist(wishlist, wishlistDto.getEventIds());
        var savedWishlist = wishlistRepository.save(wishlist);

        return wishListMapper.mapToEventDestination(savedWishlist);
    }

    @Override
    public WishlistDto update(int id, WishlistDto wishlistDto) {
        var user = authService.myDaoOrFail();

        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new NotFoundException("wishlist"));
        var isOwner = wishlist.getUser().getId().equals(user.getId());
        if (!isOwner) {
            throw new NotFoundException("wishlist");
        }
        wishlist.setName(wishlistDto.getName());

        wishlist.getEvents().clear();
        addEventsToWishlist(wishlist, wishlistDto.getEventIds());
        wishlistRepository.save(wishlist);

        return wishListMapper.mapToEventDestination(wishlist);
    }

    @Override
    public void delete(int id) {
        var loggedInUser = authService.myDaoOrFail();
        var wishlist = wishlistRepository.findById(id).orElseThrow(WishlistNotFoundException::new);

        var isOwner = wishlist.getUser().getId().equals(loggedInUser.getId());
        if (!isOwner && !loggedInUser.isAdmin()) {
            throw new WishlistNotFoundException();
        }
        wishlistRepository.deleteById(id);
    }

    private void addEventsToWishlist(Wishlist wishlist, List<Integer> eventIds){
        if (eventIds == null) {
            return;
        }
        for (var eventId : eventIds) {
            var event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
            wishlist.getEvents().add(event);
        }
    }
}