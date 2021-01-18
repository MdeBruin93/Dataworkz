package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUserId(int userId);
    Optional<Wishlist> findByIdAndUserId(int id, int userId);
}
