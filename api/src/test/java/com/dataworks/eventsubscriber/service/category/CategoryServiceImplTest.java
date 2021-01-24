package com.dataworks.eventsubscriber.service.category;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.exception.category.CategoryNotFoundException;
import com.dataworks.eventsubscriber.mapper.CategoryMapper;
import com.dataworks.eventsubscriber.model.dao.Category;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import com.dataworks.eventsubscriber.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    Category category;
    @Mock
    CategoryDto categoryDto;
    @Mock
    CategoryMapper categoryMapper;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    public void storeShouldSaveCategory_ShouldBeSuccessful() {
        // given
        var categoryDto = new CategoryDto();

        when(categoryMapper.mapToCategorySource(categoryDto)).thenReturn(category);
        when(categoryMapper.mapToCategoryDestination(category)).thenReturn(categoryDto);
        when(categoryRepository.save(category)).thenReturn(category);

        // when
        categoryService.store(categoryDto);

        // then
        verify(categoryMapper, times(1)).mapToCategorySource(categoryDto);
        verify(categoryMapper, times(1)).mapToCategoryDestination(category);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void updateWhenCategoryNotFound_ThenThrowException() {
        //given
        var categoryId = 1;
        var categoryDto = new CategoryDto();
        //when
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        //then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> categoryService.update(categoryId, categoryDto));
        verify(categoryRepository, times(1)).findById(categoryId);
        verifyNoInteractions(categoryMapper);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void updateShouldUpdateCategory_ShouldBeSuccessful() {
        // given
        var categoryName = "new category name";
        var categoryId = 1;
        var categoryDto = new CategoryDto();
        categoryDto.setName(categoryName);
        categoryDto.setColor("123456");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.mapToCategoryDestination(category)).thenReturn(categoryDto);
        when(categoryRepository.save(category)).thenReturn(category);

        // when
        var updated = categoryService.update(categoryId, categoryDto);

        // then
        assertThat(updated.getName()).isEqualTo(categoryName);
        verify(categoryRepository, times(1)).findById(anyInt());
        verify(categoryMapper, times(1)).mapToCategoryDestination(category);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void findAllShouldFindAllCategories_ShouldBeSuccessful() {
        // given
        category.setId(1);
        var categories = new ArrayList<Category>();
        categories.add(category);

        when(categoryMapper.mapToCategoryDestination(category)).thenReturn(categoryDto);
        when(categoryRepository.findAll(Sort.by("name").descending())).thenReturn(categories);

        // when
        var retrieved = categoryService.findAll();

        // then
        assertThat(retrieved.size()).isEqualTo(1);
        verify(categoryMapper, times(1)).mapToCategoryDestination(category);
        verify(categoryRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    public void findByIdFindASpecificCategory_ShouldBeSuccessful() {
        // given
        var categoryId = 1;
        category.setId(categoryId);

        when(categoryMapper.mapToCategoryDestination(category)).thenReturn(categoryDto);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        // when
        var retrieved = categoryService.findById(categoryId);

        // then
        assertThat(retrieved).isNotNull();
        verify(categoryMapper, times(1)).mapToCategoryDestination(category);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void findByIdFindASpecificCategory_ShouldThrowCategoryNotFoundException() {
        // given
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when
        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> categoryService.findById(anyInt()));

        // then
        verify(categoryRepository, times(1)).findById(anyInt());
    }

    @Test
    public void deleteDeleteASpecificCategory_ShouldThrowCategoryNotFoundException() {
        // given
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when
        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> categoryService.delete(anyInt()));

        // then
        verify(categoryRepository, times(1)).findById(anyInt());
    }

    @Test
    public void deleteDeleteASpecificCategory_ShouldBeSuccessful() {
        // given
        var categoryId = 1;
        category.setId(categoryId);

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        // when
        categoryService.delete(categoryId);

        // then
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void deleteExpiredWhenNoCategoriesFound_ThenDoNothing() {
        //given
        var returnedList = new ArrayList<Category>();
        //when
        when(categoryRepository.findAllByEndDateLessThanEqualAndDeletedIsFalse(any(LocalDate.class))).thenReturn(returnedList);

        //then
        categoryService.deleteExpired();
        verify(categoryRepository, times(1)).findAllByEndDateLessThanEqualAndDeletedIsFalse(any(LocalDate.class));
        verify(categoryRepository, times(0)).findById(anyInt());
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    public void deleteExpiredWhenCategoriesFound_ThenDelete() {
        //given
        var category = new Category();
        category.setId(1);
        var returnedList = new ArrayList<Category>();
        returnedList.add(category);
        //when
        when(categoryRepository.findAllByEndDateLessThanEqualAndDeletedIsFalse(any(LocalDate.class))).thenReturn(returnedList);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        //then
        categoryService.deleteExpired();
        verify(categoryRepository, times(1)).findAllByEndDateLessThanEqualAndDeletedIsFalse(any(LocalDate.class));
        verify(categoryRepository, times(1)).findById(category.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}