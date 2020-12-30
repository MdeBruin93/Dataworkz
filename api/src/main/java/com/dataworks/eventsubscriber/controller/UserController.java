package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final EventService eventService;

    @GetMapping("/subscriptions")
    public ResponseEntity subscriptions() {
        try {
            return new ResponseEntity(eventService.findBySubscribedUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}