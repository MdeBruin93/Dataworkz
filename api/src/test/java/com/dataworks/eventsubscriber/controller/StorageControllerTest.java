package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.storage.StorageException;
import com.dataworks.eventsubscriber.model.dto.FileDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.storage.StorageService;
import com.dataworks.eventsubscriber.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StorageController.class)
public class StorageControllerTest {
    @MockBean
    UserService userService;
    @MockBean
    StorageService storageService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void uploadWithNotAuthenticatedUser_Unauthorized() throws Exception {
        //given
        MockMultipartFile mockFile = new MockMultipartFile("file", "DATADATADATDATADATA".getBytes());
        //when

        //then
        mockMvc.perform(
            multipart("/api/storage/upload")
                .file(mockFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void uploadEmptyFile_Conflict() throws Exception {
        //given
        MockMultipartFile mockFile = new MockMultipartFile("file", "".getBytes());
        //when
        when(storageService.store(isA(MultipartFile.class))).thenThrow(StorageException.class);
        //then
        mockMvc.perform(
                multipart("/api/storage/upload")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "ricky@hr.nl", password = "123456", roles = "USER")
    void uploadFile_Success() throws Exception {
        //given
        MockMultipartFile mockFile = new MockMultipartFile("file", "".getBytes());
        //when
        when(storageService.store(isA(MultipartFile.class))).thenReturn(new FileDto());
        //then
        mockMvc.perform(
                multipart("/api/storage/upload")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
