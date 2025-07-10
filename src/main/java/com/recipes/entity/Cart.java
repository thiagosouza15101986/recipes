package com.recipes.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "total_in_cents", nullable = false)
    private Long totalInCents;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProductItem> productItems;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartRecipeItem> recipeItems;
   
    public List<CartRecipeItem> getRecipeItems() {
    	if(recipeItems == null) {
    		recipeItems = new ArrayList<>();
    	}
    	return recipeItems;
    }
   
    public List<CartProductItem> getProductItems() {
    	if(productItems == null) {
    		productItems = new ArrayList<>();
    	}
    	return productItems;
    }

    public Long getTotalInCents() {
    	return this.totalInCents == null ? 0l : totalInCents;
    }
}