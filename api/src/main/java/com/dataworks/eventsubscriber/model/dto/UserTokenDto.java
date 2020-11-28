package com.dataworks.eventsubscriber.model.dto;

import com.dataworks.eventsubscriber.model.dao.TokenType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTokenDto {
    private int userId;
    private String token;
    private TokenType type;
    private boolean tokenIsUsed;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime deleted;
}
