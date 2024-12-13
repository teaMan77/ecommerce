package com.example.project.service;

import com.example.project.exceptions.APIException;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.model.Category;
import com.example.project.payload.CategoryDTO;
import com.example.project.payload.CategoryResponse;
import com.example.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<Category> categories = categoryPage.getContent();

        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category existingCategory = categoryRepository.findByCategoryName(
                category.getCategoryName());

        if (existingCategory != null) {
            throw new APIException("Category with the name " +
                    categoryDTO.getCategoryName() + " already exists !!!");
        }
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO udpateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category optionalCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        optionalCategory.setCategoryName(categoryDTO.getCategoryName());
        CategoryDTO savedCategory = modelMapper.map(
                categoryRepository.save(optionalCategory), CategoryDTO.class);
        return savedCategory;
    }
}
