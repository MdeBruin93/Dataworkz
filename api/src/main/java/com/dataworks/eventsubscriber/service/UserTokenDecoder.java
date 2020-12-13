package com.dataworks.eventsubscriber.service;

import com.dataworks.eventsubscriber.model.DecodedTokenModel;
import com.dataworks.eventsubscriber.model.dao.TokenType;

import java.util.Base64;

public class UserTokenDecoder {
    private String token;

    public UserTokenDecoder(String encryptedToken) {
        this.token = encryptedToken;
    }

    public DecodedTokenModel getDecodedToken() {
        var decrypted = new String(Base64.getDecoder().decode(token.getBytes()));
        var splitted = decrypted.split("\\:");
        return new DecodedTokenModel(splitted[0], splitted[1], TokenType.valueOf(splitted[2]));
    }
}
