package com.dataworks.eventsubscriber.model;

import com.dataworks.eventsubscriber.model.dao.TokenType;
import lombok.Data;
import lombok.Getter;

@Getter
public class DecodedTokenModel {
    public DecodedTokenModel(String token, String email, TokenType tokenType) {
        this.token = token;
        this.email = email;
        this.tokenType = tokenType;
    }

    private String token;
    private String email;
    private TokenType tokenType;
}
