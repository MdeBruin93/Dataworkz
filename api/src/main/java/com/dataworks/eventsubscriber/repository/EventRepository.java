package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByIdAndUser_Id(int id, int userId);
    Optional<Event> findByIdAndSubscribedUsers_Id(int id, int userId);
    List<Event> findByUserId(int userId);
    List<Event> findBySubscribedUsers_Id(int userId);
    List<Event> findByCategoryId(int categoryId);
}