package com.dataworks.eventsubscriber.repository;

import com.dataworks.eventsubscriber.enums.TokenType;
import com.dataworks.eventsubscriber.model.dao.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
    Optional<UserToken> findByToken(String token);
    Optional<UserToken> findByTokenAndTypeAndTokenIsUsed(String token, TokenType type, Boolean tokenIsUsed);
}
