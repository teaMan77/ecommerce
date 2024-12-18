package com.example.project.service;

import com.example.project.payload.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();
}
