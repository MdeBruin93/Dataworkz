package com.dataworks.eventsubscriber.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    @PostMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("hello world!");
    }
}
