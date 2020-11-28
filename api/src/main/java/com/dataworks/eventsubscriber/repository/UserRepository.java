package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
