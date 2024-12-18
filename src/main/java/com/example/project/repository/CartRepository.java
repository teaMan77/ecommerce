package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
