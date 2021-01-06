package com.dataworks.eventsubscriber.service.category;

import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto store(CategoryDto categoryDto);
    CategoryDto update(int id, CategoryDto categoryDto);
    List<CategoryDto> findAll();
    CategoryDto findById(int id);
    CategoryDto findByEventId(int eventId);
    void delete(int eventId);
}
