package com.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipes.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}