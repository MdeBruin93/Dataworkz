package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.Wishlist;
import com.dataworks.eventsubscriber.model.dto.WishlistDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class WishListMapper {
    public abstract Wishlist mapToEventSource(WishlistDto destination);

    public abstract WishlistDto mapToEventDestination(Wishlist savedWishlist);

    public abstract List<WishlistDto> mapToEventDestinationCollection(List<Wishlist> savedWishlists);
}
