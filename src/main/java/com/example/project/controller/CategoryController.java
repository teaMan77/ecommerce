package com.example.project.controller;

import com.example.project.model.Category;
import com.example.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("public/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(),
                HttpStatus.OK);
    }

    @PostMapping("public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category created", HttpStatus.OK);
    }

    @DeleteMapping("admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            return new ResponseEntity(categoryService.deleteCategory(categoryId),
                    HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity(e.getReason(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("public/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category,
                                                 @PathVariable Long categoryId) {
        try {
            return new ResponseEntity<>(categoryService.udpateCategory(category, categoryId),
                    HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity(e.getReason(), HttpStatus.NOT_FOUND);
        }
    }
}
