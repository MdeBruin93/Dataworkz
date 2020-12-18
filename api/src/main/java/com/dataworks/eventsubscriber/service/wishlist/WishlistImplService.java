package com.dataworks.eventsubscriber.service.wishlist;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.mapper.EventMapper;
import com.dataworks.eventsubscriber.mapper.WishListMapper;
import com.dataworks.eventsubscriber.model.dao.Wishlist;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import com.dataworks.eventsubscriber.repository.WishlistRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WishlistImplService implements WishlistService {

    private final AuthService authService;
    private final WishlistRepository wishlistRepository;
    private final WishListMapper wishListMapper;
    private final EventMapper eventMapper;

    @Override
    public List<WishlistDto> findByUserId() {
        var loggedInUser = authService.myDao();
        var wishlists = wishlistRepository.findByUserId(loggedInUser.getId());
        return wishListMapper.mapToEventDestinationCollection(wishlists);
    }

    @Override
    public WishlistDto store(WishlistDto wishlistDto) {
        var loggedInUser = authService.myDaoOrFail();

        var mappedWishlist = wishListMapper.mapToEventSource(wishlistDto);
        mappedWishlist.setUser(loggedInUser);
        var savedWishlist = wishlistRepository.save(mappedWishlist);

        return wishListMapper.mapToEventDestination(savedWishlist);
    }

    @Override
    public WishlistDto update(int id, WishlistDto wishlistDto) {
        Optional<Wishlist> wishlistFromRepo = wishlistRepository.findById(id);

        if (wishlistFromRepo.isEmpty()) {
            throw new NotFoundException("Wishlist");
        }

        var wishlist = wishlistFromRepo.get();
        wishlist.setName(wishlistDto.getName());

        wishlistRepository.deleteEventsById(id);

        for (var event : wishlistDto.getEvents()) {
            wishlist.getEvents().add(eventMapper.mapToEventSource(event));
        }
        wishlistRepository.save(wishlist);

        return wishListMapper.mapToEventDestination(wishlistRepository.save(wishlist));
    }
}