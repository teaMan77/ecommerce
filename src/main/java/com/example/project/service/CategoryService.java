package com.example.project.service;

import com.example.project.model.Category;
import com.example.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category udpateCategory(Category category, Long categoryId);
}
