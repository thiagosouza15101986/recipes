package com.recipes.service;

import com.recipes.dto.response.CartResponseDto;
import com.recipes.entity.Cart;

public interface CartService {
	public Cart findById(Long id);

	public CartResponseDto getCartById(Long id);

	public CartResponseDto addRecipe(Long cartId, Long recipeId);

	public CartResponseDto removeRecipe(Long cartId, Long recipeId);
}