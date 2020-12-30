package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUserId(int userId);
    Optional<Wishlist> findByIdAndUserId(int id, int userId);

//    @Modifying
//    @Query("Delete from java_minor.wishlist_event where wishlist_id=:wishlist_id")
//    void deleteEventsById(@Param("wishlist_id") int id);
}
