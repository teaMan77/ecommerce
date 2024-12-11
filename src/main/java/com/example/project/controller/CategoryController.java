package com.example.project.controller;

import com.example.project.model.Category;
import com.example.project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("api/public/categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("api/public/categories")
    public String createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return "Category created";
    }

    @DeleteMapping("api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            return new ResponseEntity(categoryService.deleteCategory(categoryId), HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity(e.getReason(), HttpStatus.NOT_FOUND);
        }
    }
}
