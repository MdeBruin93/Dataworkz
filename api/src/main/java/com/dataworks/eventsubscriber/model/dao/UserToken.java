package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserToken extends BaseDao {

    private int userId;
    private String token;
    private TokenType type;
    private boolean tokenIsUsed;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime deleted;
}