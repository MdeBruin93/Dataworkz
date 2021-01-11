package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
