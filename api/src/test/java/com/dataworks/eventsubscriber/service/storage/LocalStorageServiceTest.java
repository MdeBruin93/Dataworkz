package com.dataworks.eventsubscriber.service.storage;

import com.dataworks.eventsubscriber.exception.storage.StorageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.core.Path;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { LocalStorageService.class })
@TestPropertySource(properties = { "upload.path=/upload/" })
class LocalStorageServiceTest {
    @Mock
    MultipartFile multipartFile;
    @InjectMocks
    LocalStorageService localStorageService;
    @Test
    void storeWhenFileIsEmpty_ThenThrowStorageException() {
        //given

        //when //then
        when(multipartFile.isEmpty()).thenReturn(true);
        assertThatExceptionOfType(StorageException.class)
                .isThrownBy(() -> localStorageService.store(multipartFile));
    }

    @Test
    void storeWhenFileIsValid_ThenSave() {
        //given
        var storagePath = "/upload/";
        var fileName = "test.png";
        var expectedPath = new Path(Paths.get("").toAbsolutePath().normalize().toString() + storagePath + fileName);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, fileName, null, "content".getBytes());
        //when

        //then
        var result = localStorageService.store(mockMultipartFile);
        assertThat(result).isInstanceOf(String.class);
        assertThat(result).isEqualTo(fileName);
    }
}