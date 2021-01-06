package com.dataworks.eventsubscriber.service.category;

import com.dataworks.eventsubscriber.exception.NotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.mapper.CategoryMapper;
import com.dataworks.eventsubscriber.model.dao.Category;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import com.dataworks.eventsubscriber.repository.CategoryRepository;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryImplService implements CategoryService {
    private final AuthService authService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto store(CategoryDto categoryDto) {
        var mappedCategory = categoryMapper.mapToEventSource(categoryDto);

        var events = new ArrayList<Event>();
        for (var eventId: categoryDto.getEventIds()) {
            var event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
            events.add(event);
        }
        mappedCategory.setEvents(events);

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
    public List<CategoryDto> findByEventId() {
        return null;
    }

    @Override
    public void delete(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
