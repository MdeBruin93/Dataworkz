package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Category;
import com.dataworks.eventsubscriber.model.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByEndDateAndDeletedIsFalse(LocalDate date);
    Optional<Category> findByIdAndDeletedIsFalse(int eventId);
}
