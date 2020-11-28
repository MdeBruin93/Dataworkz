package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.model.dao.TokenType;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
    UserToken findByToken(String token, TokenType type);
}
