package com.recipes.service.impl;

import com.recipes.dto.response.CartProductItemResponseDto;
import com.recipes.dto.response.CartRecipeItemResponseDto;
import com.recipes.dto.response.CartResponseDto;
import com.recipes.entity.Cart;
import com.recipes.entity.CartProductItem;
import com.recipes.entity.CartRecipeItem;
import com.recipes.entity.Recipe;
import com.recipes.exception.ResourceNotFoundException;
import com.recipes.repository.CartRepository;
import com.recipes.service.CartService;
import com.recipes.service.RecipeService;
import com.recipes.util.retry.DatabaseRetryable;

import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
    private RecipeService recipeService;

	@Autowired
    private CartRepository cartRepository;

	@Override
	@Cacheable(value = "carts", key = "#id")
    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public CartResponseDto getCartById(Long id) {
        return toDto(cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found")));
    }

    @Override
    @Transactional
 // Update cache when a recipe is added
    @CachePut(value = "carts", key = "#cartId")
    @DatabaseRetryable
    public CartResponseDto addRecipe(Long cartId, Long recipeId) {
        Cart cart = findById(cartId);

        Recipe recipe = recipeService.findById(recipeId);

        CartRecipeItem item = cart.getRecipeItems().stream()
            .filter(i -> i.getRecipeId().equals(recipeId))
            .findFirst()
            .orElse(null);

        if (item == null) {
            item = CartRecipeItem.builder()
            		.recipeId(recipeId)
            		.recipe(recipe)
            		.quantity(1)
            		.cartId(cart.getId())
            		.build();
            cart.getRecipeItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }

        cart.setTotalInCents(cart.getTotalInCents() + recipe.getPriceInCents());

        cartRepository.save(cart);
        return toDto(cart);
    }

    @Override
    @Transactional
 // Evict cache when a cart is deleted (if you have such operation)
    @CacheEvict(value = "carts", key = "#cartId")
    @DatabaseRetryable
    public CartResponseDto removeRecipe(Long cartId, Long recipeId) {
    	Cart cart = findById(cartId);

	    CartRecipeItem item = cart.getRecipeItems().stream()
	        .filter(i -> i.getRecipeId().equals(recipeId))
	        .findFirst()
	        .orElse(null);

	    if (item != null) {
	    	item.setQuantity(item.getQuantity() - 1);
	        cart.setTotalInCents(cart.getTotalInCents() - item.getRecipe().getPriceInCents());
	        if (item.getQuantity() <= 0) {
	            cart.getRecipeItems().remove(item);
	        }
	    }

	    cartRepository.save(cart);
	    return toDto(cart);
    }

    private CartResponseDto toDto(Cart cart) {
        Long total = 0L;
        for (CartRecipeItem item : cart.getRecipeItems()) {
            total += item.getQuantity() * (item.getRecipe() != null ? item.getRecipe().getPriceInCents() : 0);
        }
        for (CartProductItem item : cart.getProductItems()) {
            total += item.getQuantity() * (item.getProduct() != null ? item.getProduct().getPriceInCents() : 0);
        }
        cart.setTotalInCents(total);

        return CartResponseDto.builder()
        	    .id(cart.getId())
        	    .totalInCents(total)
        	    .recipeItems(
        	        cart.getRecipeItems() == null
        	            ? Collections.emptyList()
        	            : cart.getRecipeItems().stream()
        	                .map(item -> CartRecipeItemResponseDto.builder()
        	                    .recipeId(item.getRecipeId())
        	                    .quantity(item.getQuantity())
        	                    .build())
        	                .toList()
        	    )
        	    .productItems(
        	        cart.getProductItems() == null
        	            ? Collections.emptyList()
        	            : cart.getProductItems().stream()
        	                .map(item -> CartProductItemResponseDto.builder()
        	                    .productId(item.getProductId())
        	                    .quantity(item.getQuantity())
        	                    .build())
        	                .toList()
        	    )
        	    .build();
    }
}