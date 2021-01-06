package com.dataworks.eventsubscriber.service.category;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.mapper.CategoryMapper;
import com.dataworks.eventsubscriber.model.dao.Category;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import com.dataworks.eventsubscriber.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryImplService implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto store(CategoryDto categoryDto) {
        var mappedCategory = categoryMapper.mapToEventSource(categoryDto);

        var savedCategory = categoryRepository.save(mappedCategory);

        return categoryMapper.mapToEventDestination(savedCategory);
    }

    @Override
    public CategoryDto update(int id, CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new NotFoundException("Category");
        }
        Category cat = category.get();
        cat.setName(categoryDto.getName());
        cat.setColor(categoryDto.getColor());

        return categoryMapper.mapToEventDestination(categoryRepository.save(cat));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll(Sort.by("date").descending())
                .stream()
                .map(categoryMapper::mapToEventDestination)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(int id) {
        var event = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category"));
        return categoryMapper.mapToEventDestination(event);
    }

    @Override
    public CategoryDto findByEventId(int eventId) {
        return categoryRepository.findByEvents_id(eventId)
                .stream()
                .map(categoryMapper::mapToEventDestination)
                .findFirst().orElseThrow(() -> new NotFoundException("Category"));
    }

    @Override
    public void delete(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}