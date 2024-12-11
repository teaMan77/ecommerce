package com.example.project.service;

import com.example.project.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category udpateCategory(Category category, Long categoryId);
}
