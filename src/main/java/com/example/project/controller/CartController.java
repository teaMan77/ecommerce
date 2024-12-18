package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.project.payload.CartDTO;
import com.example.project.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/carts/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, 
                                                        @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<List<CartDTO>>(cartDTOS, HttpStatus.OK);
    }
}
