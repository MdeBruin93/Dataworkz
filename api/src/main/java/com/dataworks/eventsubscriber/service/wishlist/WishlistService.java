package com.dataworks.eventsubscriber.service.wishlist;

import com.dataworks.eventsubscriber.model.dto.WishlistDto;

import java.util.List;

public interface WishlistService {
    List<WishlistDto> findByUserId();
    WishlistDto findByIdAndUserId(int id);
    WishlistDto store(WishlistDto wishlistDto);
    WishlistDto update(int id, WishlistDto wishlistDto);
    void delete(int id);
}
