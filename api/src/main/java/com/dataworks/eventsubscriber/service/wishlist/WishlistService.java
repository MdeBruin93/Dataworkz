package com.dataworks.eventsubscriber.service.wishlist;

import com.dataworks.eventsubscriber.model.dto.WishlistDto;

import java.util.List;

public interface WishlistService {
    List<WishlistDto> findByUserId();
}
