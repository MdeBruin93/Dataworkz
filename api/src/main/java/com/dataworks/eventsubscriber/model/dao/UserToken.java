package com.dataworks.eventsubscriber.model.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserToken {
    private int id;
    private int userId;
    private String token;
    private TokenType type;
    private boolean tokenIsUsed;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime deleted;
}