package com.recipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductItemResponseDto {
	private Long id;
    
    private Long productId;
    
    private String productName;

    private int quantity;
}