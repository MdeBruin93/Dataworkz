package com.dataworks.eventsubscriber.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailProvider {
    void send(String email, String token);
}