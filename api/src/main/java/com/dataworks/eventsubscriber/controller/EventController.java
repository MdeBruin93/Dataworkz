package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventUserAlreadySubscribedException;
import com.dataworks.eventsubscriber.exception.user.UserNotFoundException;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("")
    public ResponseEntity store(@Valid @RequestBody EventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);

        try {
            var event = eventService.store(eventDto);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @Valid @RequestBody EventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);

        try {
            var event = eventService.update(id, eventDto);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EventNotFoundException | UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity all() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        try {
            return new ResponseEntity(eventService.findById(id), HttpStatus.OK);
        } catch (EventNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity subscribe(@PathVariable int id) {
        try {
            return new ResponseEntity(eventService.subscribe(id), HttpStatus.OK);
        } catch (EventNotFoundException | UserNotFoundException exception) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (EventUserAlreadySubscribedException exception) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        try {
            eventService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
