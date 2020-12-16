package com.dataworks.eventsubscriber.service.wishlist;

import com.dataworks.eventsubscriber.mapper.WishListMapper;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import com.dataworks.eventsubscriber.repository.WishlistRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WishlistImplService implements WishlistService {

    private final AuthService authService;
    private final WishlistRepository wishlistRepository;
    private final WishListMapper wishListMapper;

    @Override
    public List<WishlistDto> findByUserId() {
        var loggedInUser = authService.myDao();
        var wishlists = wishlistRepository.findByUserId(loggedInUser.getId());
        return wishListMapper.mapToEventDestinationCollection(wishlists);
    }
}
