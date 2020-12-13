package com.dataworks.eventsubscriber.service.storage;

import com.dataworks.eventsubscriber.exception.storage.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.Path;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class LocalStorageService implements StorageService {
    private final String storagePath = "/src/main/resources/static/";

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        try {
            var fileName = file.getOriginalFilename();
            var is = file.getInputStream();

            var fileStore = new Path(Paths.get("").toAbsolutePath().normalize().toString() + storagePath + fileName);

            Files.copy(is, Paths.get(fileStore.toString()),
                    StandardCopyOption.REPLACE_EXISTING);

            return file.getOriginalFilename();
        } catch (IOException e) {

            var msg = String.format("Failed to store file %s", file.getName());

            throw new StorageException(msg, e);
        }
    }
}
