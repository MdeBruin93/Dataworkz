package com.dataworks.eventsubscriber.service.storage;

import com.dataworks.eventsubscriber.exception.storage.StorageException;
import com.dataworks.eventsubscriber.model.dto.FileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalStorageService implements StorageService {
    private final Path root = Paths.get("src/main/storage");

    @Override
    public FileDto store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Could not store the file. Because file is empty!");
        }

        try {
            Files.copy(
                    file.getInputStream(),
                    this.root.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING
            );
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/storage/")
                    .path(file.getOriginalFilename())
                    .toUriString();

            var fileDto = new FileDto();
            fileDto.setFileUrl(fileDownloadUri);

            return fileDto;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
