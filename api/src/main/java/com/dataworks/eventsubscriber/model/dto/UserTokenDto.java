package com.dataworks.eventsubscriber.model.dto;

import com.dataworks.eventsubscriber.enums.TokenType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
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
