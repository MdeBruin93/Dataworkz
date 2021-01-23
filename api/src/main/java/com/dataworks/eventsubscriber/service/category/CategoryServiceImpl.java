package com.dataworks.eventsubscriber.service.category;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.exception.category.CategoryContainEventsException;
import com.dataworks.eventsubscriber.exception.category.CategoryNotFoundException;
import com.dataworks.eventsubscriber.mapper.CategoryMapper;
import com.dataworks.eventsubscriber.model.dao.Category;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import com.dataworks.eventsubscriber.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto store(CategoryDto categoryDto) {
        var mappedCategory = categoryMapper.mapToCategorySource(categoryDto);

        var savedCategory = categoryRepository.save(mappedCategory);

        return categoryMapper.mapToCategoryDestination(savedCategory);
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

        return categoryMapper.mapToCategoryDestination(categoryRepository.save(cat));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll(Sort.by("name").descending())
                .stream()
                .map(categoryMapper::mapToCategoryDestination)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(int id) {
        var category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        return categoryMapper.mapToCategoryDestination(category);
    }

    @Override
    public void delete(int categoryId) {
        var foundCategory = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        foundCategory.setDeleted(true);

        categoryRepository.save(foundCategory);
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    public void deleteExpired() {
        categoryRepository.findAllByEndDateAndDeletedIsFalse(LocalDate.now())
                .forEach((category) -> {
                    System.out.println("Delete category:" + category.getId());

                    this.delete(category.getId());
                });
    }
}