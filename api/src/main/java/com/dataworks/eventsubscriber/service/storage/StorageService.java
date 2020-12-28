package com.dataworks.eventsubscriber.service.storage;

import com.dataworks.eventsubscriber.model.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface StorageService {
    FileDto store(MultipartFile file);
}
