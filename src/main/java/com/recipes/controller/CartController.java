package com.recipes.controller;

import com.recipes.service.CartService;
import com.recipes.dto.AddRecipeToCartDto;
import com.recipes.dto.response.CartResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
	@Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    // Returns a Cart by its ID
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @PostMapping("/{id}/add_recipe")
    // Adds a recipe to a cart
    public ResponseEntity<CartResponseDto> addRecipeToCart(@PathVariable Long id, @RequestBody AddRecipeToCartDto addRecipeToCartDto) {
        return ResponseEntity.ok(
                cartService.addRecipe(id, addRecipeToCartDto.getRecipeId()));
    }

    @DeleteMapping("/{id}/recipes/{idRecipes}")
    // Removes a recipe from a cart
    public ResponseEntity<CartResponseDto> deleteRecipeFromCart(@PathVariable Long id, @PathVariable Long idRecipes) {
        return ResponseEntity.ok(cartService.removeRecipe(id, idRecipes));
    }
}