package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
