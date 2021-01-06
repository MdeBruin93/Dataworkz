package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByEventId(int eventId);
}
