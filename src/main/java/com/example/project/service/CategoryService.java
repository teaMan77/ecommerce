package com.example.project.service;

import com.example.project.payload.CategoryDTO;
import com.example.project.payload.CategoryResponse;


public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO udpateCategory(CategoryDTO categoryDTO, Long categoryId);
}
