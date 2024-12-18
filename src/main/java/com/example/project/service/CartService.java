package com.example.project.service;

import com.example.project.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

}
