package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByEvent(int eventId);
}
