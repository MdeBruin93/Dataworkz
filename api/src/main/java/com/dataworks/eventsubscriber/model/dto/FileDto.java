package com.dataworks.eventsubscriber.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Component
@NoArgsConstructor
public class FileDto {
    private String fileUrl;
}
