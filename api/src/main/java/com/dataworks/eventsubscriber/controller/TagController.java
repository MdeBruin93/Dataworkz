package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.model.dto.TagDto;
import com.dataworks.eventsubscriber.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class TagController {
    final TagService tagService;

    @PostMapping("")
    public ResponseEntity store(@Valid @RequestBody TagDto tagDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            var result = tagService.store(tagDto);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (EventNotFoundException eventNotFoundException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}