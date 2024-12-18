package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
