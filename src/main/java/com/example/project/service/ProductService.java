package com.example.project.service;

import com.example.project.payload.ProductDTO;
import com.example.project.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts();
}
