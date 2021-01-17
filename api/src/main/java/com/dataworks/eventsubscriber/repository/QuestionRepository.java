package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
