package com.example.project.repository;

import com.example.project.model.Category;
import com.example.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceDesc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String s);
}
