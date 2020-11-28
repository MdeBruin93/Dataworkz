package com.dataworks.eventsubscriber.model.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserToken extends BaseDao {

    private int userId;
    private String token;
    private TokenType type;
    private boolean tokenIsUsed;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime deleted;
}