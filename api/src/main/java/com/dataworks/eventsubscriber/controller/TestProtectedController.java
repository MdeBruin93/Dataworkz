package com.dataworks.eventsubscriber.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is only used for testing route protection purposes.
 */
@RestController
@RequestMapping("test")
@NoArgsConstructor
public class TestProtectedController {
    @GetMapping("/user")
    public ResponseEntity user() {
        return new ResponseEntity<>("Hello user :-)", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity admin() {
        return new ResponseEntity<>("Hello admin :-)", HttpStatus.OK);
    }
}
