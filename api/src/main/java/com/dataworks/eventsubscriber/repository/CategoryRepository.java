package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByEvents_id(int eventId);
}
