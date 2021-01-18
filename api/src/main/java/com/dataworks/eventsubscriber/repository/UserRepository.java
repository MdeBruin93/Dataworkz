package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByBlocked(boolean blocked);
    Optional<User> findByEmail(String email);
    Optional<User> findByTokens(String token);
}
