package com.dataworks.eventsubscriber.model.dao;

import com.dataworks.eventsubscriber.enums.TokenType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserToken extends BaseDao {
    @ManyToOne()
    private User user;
    private String token;
    private TokenType type;
    private boolean tokenIsUsed;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime deleted;
}