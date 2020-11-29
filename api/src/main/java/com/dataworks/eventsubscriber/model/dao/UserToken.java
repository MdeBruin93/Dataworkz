package com.dataworks.eventsubscriber.model.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class UserToken extends BaseDao {

    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private TokenType type;

    @Getter
    @Setter
    private boolean tokenIsUsed;

    @Getter
    @Setter
    private LocalDateTime created;

    @Getter
    @Setter
    private LocalDateTime modified;

    @Getter
    @Setter
    private LocalDateTime deleted;
}