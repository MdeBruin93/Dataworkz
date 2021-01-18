package com.dataworks.eventsubscriber.service.token;

import com.dataworks.eventsubscriber.model.dto.TokenDto;
import org.springframework.stereotype.Service;

@Service

public interface TokenService {
    public void verify();
    public TokenDto generate();
    String encode();
    String decode();
}
