package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.storage.StorageException;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.service.storage.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @Operation(
            summary = "Upload file",
            description = "",
            tags = { "Storage" },
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the stored file",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "401", description = "User is not authorized"),
            @ApiResponse(responseCode = "409", description = "The given file is empty")})
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        try {
            var storedFile = storageService.store(file);
            return new ResponseEntity<>(storedFile, HttpStatus.OK);
        } catch (StorageException exception) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}
