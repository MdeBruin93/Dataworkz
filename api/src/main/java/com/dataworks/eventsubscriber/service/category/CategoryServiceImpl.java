package com.dataworks.eventsubscriber.service.category;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.exception.category.CategoryContainEventsException;
import com.dataworks.eventsubscriber.exception.category.CategoryNotFoundException;
import com.dataworks.eventsubscriber.mapper.CategoryMapper;
import com.dataworks.eventsubscriber.model.dao.Category;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import com.dataworks.eventsubscriber.repository.CategoryRepository;
import com.dataworks.eventsubscriber.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

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
        return categoryRepository.findAll(Sort.by("name").descending())
                .stream()
                .map(categoryMapper::mapToEventDestination)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(int id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category"));
        return categoryMapper.mapToEventDestination(category);
    }

    @Override
    public void delete(int categoryId) {
        var foundCategory = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        var countedEvents = (long) foundCategory.getEvents().size();
        if (countedEvents > 0) {
            throw new CategoryContainEventsException();
        }
        categoryRepository.deleteById(categoryId);
    }
}