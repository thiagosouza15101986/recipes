package com.recipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRecipeItemResponseDto {
    private Long id;
    
    private Long recipeId;
    
    private String recipeName;

    private int quantity;
}