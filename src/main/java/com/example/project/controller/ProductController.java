package com.example.project.controller;

import com.example.project.config.PageConstants;
import com.example.project.payload.ProductDTO;
import com.example.project.payload.ProductResponse;
import com.example.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
        return new ResponseEntity<ProductDTO>(productService.addProduct(productDTO, categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = PageConstants.PAGE_NUMBER,
                    required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",  defaultValue = PageConstants.PAGE_SIZE,
                    required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstants.SORT_PRODUCT_BY,
                    required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = PageConstants.SORT_ORDER,
                    required = false) String sortOrder) {
        return new ResponseEntity<ProductResponse>(productService.getAllProducts(
                pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = PageConstants.PAGE_NUMBER,
                    required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",  defaultValue = PageConstants.PAGE_SIZE,
                    required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstants.SORT_PRODUCT_BY,
                    required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = PageConstants.SORT_ORDER,
                    required = false) String sortOrder) {
        return new ResponseEntity<ProductResponse>(productService.getProductsByCategoryId(
                categoryId, pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @Valid @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = PageConstants.PAGE_NUMBER,
                    required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",  defaultValue = PageConstants.PAGE_SIZE,
                    required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstants.SORT_PRODUCT_BY,
                    required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = PageConstants.SORT_ORDER,
                    required = false) String sortOrder) {
        return new ResponseEntity<ProductResponse>(productService.getProductsByKeyword(keyword,
                pageNumber, pageSize, sortBy, sortOrder), HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId) {
        return new ResponseEntity<ProductDTO>(productService.updateProduct(productDTO, productId), HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity<ProductDTO>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        return new ResponseEntity<ProductDTO>(productService.updateProductImage(productId, image), HttpStatus.OK);
    }
}
