package com.dataworks.eventsubscriber.exception.category;

import com.dataworks.eventsubscriber.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {
        super("Category");
    }
}