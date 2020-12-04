package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
