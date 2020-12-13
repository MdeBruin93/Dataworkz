package com.dataworks.eventsubscriber.service.storage;

import io.github.classgraph.Resource;
import org.springframework.data.rest.core.Path;
import org.springframework.web.multipart.MultipartFile;



public interface StorageService {
    String store(MultipartFile file);
}
