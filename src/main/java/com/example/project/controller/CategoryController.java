package com.example.project.controller;

import com.example.project.config.PageConstants;
import com.example.project.payload.CategoryDTO;
import com.example.project.payload.CategoryResponse;
import com.example.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
                @RequestParam(name = "pageNumber", defaultValue = PageConstants.PAGE_NUMBER,
                        required = false) Integer pageNumber,
                @RequestParam(name = "pageSize",  defaultValue = PageConstants.PAGE_SIZE,
                        required = false) Integer pageSize,
                @RequestParam(name = "sortBy", defaultValue = PageConstants.SORT_CATEGORY_BY,
                        required = false) String sortBy,
                @RequestParam(name = "sortOrder", defaultValue = PageConstants.SORT_ORDER,
                        required = false) String sortOrder) {
        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder),
                HttpStatus.OK);
    }

    @PostMapping("public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
            return new ResponseEntity<>(categoryService.deleteCategory(categoryId),
                    HttpStatus.OK);
    }

    @PutMapping("public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId) {
            return new ResponseEntity<>(categoryService.udpateCategory(categoryDTO, categoryId),
                    HttpStatus.OK);
    }
}
