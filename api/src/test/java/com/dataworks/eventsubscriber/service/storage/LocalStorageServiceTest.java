package com.dataworks.eventsubscriber.service.storage;

import com.dataworks.eventsubscriber.exception.storage.StorageException;
import com.dataworks.eventsubscriber.model.dto.FileDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

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
        //when //then
        when(multipartFile.isEmpty()).thenReturn(true);
        assertThatExceptionOfType(StorageException.class)
                .isThrownBy(() -> localStorageService.store(multipartFile));
    }

    @Test
    void storeWhenFileIsValid_ThenSave() {
        //given
        var fileName = "test.png";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, fileName, null, "content".getBytes());
        //when

        //then
        var result = localStorageService.store(mockMultipartFile);
        assertThat(result).isInstanceOf(FileDto.class);
        assertThat(result.getFileUrl()).isEqualTo("http://localhost/storage/test.png");
    }
}