package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByIdAndUser_Id(int id, int userId);
    Optional<Event> findByIdAndSubscribedUsers_Id(int id, int userId);
    List<Event> findByUserId(int userId);
}