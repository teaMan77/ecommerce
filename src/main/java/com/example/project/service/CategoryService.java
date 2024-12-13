package com.example.project.service;

import com.example.project.payload.CategoryDTO;
import com.example.project.payload.CategoryResponse;


public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO udpateCategory(CategoryDTO categoryDTO, Long categoryId);
}
