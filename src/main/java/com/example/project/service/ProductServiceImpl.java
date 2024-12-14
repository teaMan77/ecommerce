package com.example.project.service;

import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.model.Category;
import com.example.project.model.Product;
import com.example.project.payload.ProductDTO;
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setImage("default.png");
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());

        product.setDiscount(productDTO.getDiscount());
        product.setSpecialPrice(productDTO.getPrice() - ((productDTO.getDiscount() / 100) * productDTO.getPrice()));

        product.setCategory(category);

        productRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);
    }
}
