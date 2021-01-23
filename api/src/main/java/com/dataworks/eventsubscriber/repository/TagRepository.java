package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
    List<Tag> findByEvent_id(int eventId);
}
