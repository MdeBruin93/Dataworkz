package com.dataworks.eventsubscriber.controller;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.exception.category.CategoryContainEventsException;
import com.dataworks.eventsubscriber.exception.category.CategoryNotFoundException;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import com.dataworks.eventsubscriber.service.auth.WebAuthDetailService;
import com.dataworks.eventsubscriber.service.category.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @MockBean
    BindingResult bindingResult;
    @MockBean
    CategoryService categoryService;
    @MockBean
    WebAuthDetailService webAuthDetailService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "ADMIN")
    public void storeCreatesANewCategory_ShouldBeSuccessful() throws Exception {
        // given
        var categoryDto = new CategoryDto();
        categoryDto.setName("new Category name");
        categoryDto.setColor("123456");

        var json = new ObjectMapper().writeValueAsString(categoryDto);

        //when
        when(categoryService.store(any(CategoryDto.class))).thenReturn(categoryDto);

        // act & assert
        mockMvc.perform(
                post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "USER")
    public void storeCreatesANewCategory_ShouldBeUnauthorized() throws Exception {
        // given
        var categoryDto = new CategoryDto();
        categoryDto.setName("new Category name");
        categoryDto.setColor("123456");

        var json = new ObjectMapper().writeValueAsString(categoryDto);

        //when
        when(categoryService.store(any(CategoryDto.class))).thenReturn(categoryDto);

        // act & assert
        mockMvc.perform(
                post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "ADMIN")
    public void storeCreatesANewCategory_ShouldBeBadRequest() throws Exception {
        // given
        var categoryDto = new CategoryDto();

        var json = new ObjectMapper().writeValueAsString(categoryDto);

        //when

        // act & assert
        mockMvc.perform(
                post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "ADMIN")
    public void updateUpdatesTheCategory_ShouldBeSuccessful() throws Exception {
        // given
        var categoryDto = new CategoryDto();
        categoryDto.setName("new Category name");
        categoryDto.setColor("123456");

        var json = new ObjectMapper().writeValueAsString(categoryDto);

        //when
        when(categoryService.update(anyInt(), any(CategoryDto.class))).thenReturn(categoryDto);

        // act & assert
        mockMvc.perform(
                put("/api/categories/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "ADMIN")
    public void updateEmptyCategory_ShouldBeBadRequest() throws Exception {
        // given
        var categoryId = 1;
        var categoryDto = new CategoryDto();

        var json = new ObjectMapper().writeValueAsString(categoryDto);

        //when

        // act & assert
        mockMvc.perform(
                put("/api/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "ADMIN")
    public void updateUpdatesTheCategory_ShouldThrowCategoryNotFoundException() throws Exception {
        // given
        var categoryDto = new CategoryDto();
        categoryDto.setName("new Category name");
        categoryDto.setColor("123456");

        var json = new ObjectMapper().writeValueAsString(categoryDto);

        //when
        when(categoryService.update(anyInt(), any(CategoryDto.class))).thenThrow(new CategoryNotFoundException());

        // act & assert
        mockMvc.perform(
                put("/api/categories/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "USER")
    public void findAll_ShouldBeSuccessful() throws Exception {
        // given
        var categories = new ArrayList<CategoryDto>();

        //when
        when(categoryService.findAll()).thenReturn(categories);

        // act & assert
        mockMvc.perform(
                get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "USER")
    public void findByIdFindASpecificCategory_ShouldBeSuccessful() throws Exception {
        // given
        var category = new CategoryDto();

        //when
        when(categoryService.findById(anyInt())).thenReturn(category);

        // act & assert
        mockMvc.perform(
                get("/api/categories/" + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "michael@hr.nl", password = "123456", roles = "USER")
    public void findByIdFindASpecificCategory_ShouldThrowCategoryNotFoundException() throws Exception {
        // given

        //when
        when(categoryService.findById(anyInt())).thenThrow(new CategoryNotFoundException());

        // act & assert
        mockMvc.perform(
                get("/api/categories/" + 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@hr.nl", password = "123456", roles = "ADMIN")
    public void deleteWhenCategoryNotFound_ThenThrowException() throws Exception {
        // given
        var categoryId = 1;
        //when
        doThrow(NotFoundException.class).when(categoryService).delete(categoryId);

        // act & assert
        mockMvc.perform(
                delete("/api/categories/" + 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenCategoryIsAttached_ThenThrowException() throws Exception {
        // given
        var categoryId = 1;
        //when
        doThrow(CategoryContainEventsException.class).when(categoryService).delete(categoryId);

        // act & assert
        mockMvc.perform(
                delete("/api/categories/" + 1))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "user@hr.nl", password = "123456", roles = "ADMIN")
    public void deleteDeleteACategory_ShouldBeSuccessful() throws Exception {
        // given

        //when
        doNothing().when(categoryService).delete(anyInt());

        // act & assert
        mockMvc.perform(
                delete("/api/categories/" + 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}