package com.example.project.service;

import com.example.project.exceptions.APIException;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.model.Category;
import com.example.project.payload.CategoryDTO;
import com.example.project.payload.CategoryResponse;
import com.example.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public void createCategory(Category category) {
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if (existingCategory != null) {
            throw new APIException("Category with the name " +
                    category.getCategoryName() + " already exists !!!");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return category.getCategoryName() +
                " Category with ID "+ category.getCategoryId() +" deleted successfully!!";
    }

    @Override
    public Category udpateCategory(Category category, Long categoryId) {
        Category optionalCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        optionalCategory.setCategoryName(category.getCategoryName());
        Category savedCategory = categoryRepository.save(optionalCategory);
        return savedCategory;
    }
}
