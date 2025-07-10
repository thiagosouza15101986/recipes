package com.recipes.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private Long id;
    
    private Long totalInCents;

    private List<CartProductItemResponseDto> productItems;

    private List<CartRecipeItemResponseDto> recipeItems;

    public List<CartProductItemResponseDto> getProductItems() {
    	if (this.productItems == null) {
    		this.productItems = new ArrayList<>();
    	}
    	return this.productItems;
    }

    public List<CartRecipeItemResponseDto> getRecipeItems() {
    	if (this.recipeItems == null) {
    		this.recipeItems = new ArrayList<>();
    	}
    	return this.recipeItems;
    }
}