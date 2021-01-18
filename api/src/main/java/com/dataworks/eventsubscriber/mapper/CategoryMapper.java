package com.dataworks.eventsubscriber.mapper;

import com.dataworks.eventsubscriber.model.dao.Category;
import com.dataworks.eventsubscriber.model.dto.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    public abstract Category mapToCategorySource(CategoryDto destination);

    public abstract CategoryDto mapToCategoryDestination(Category savedCategory);

    public abstract List<CategoryDto> mapToCategoryDestinationCollection(List<Category> savedCategories);
}
