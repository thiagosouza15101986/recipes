package com.recipes.repository;

import com.recipes.entity.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeProductRepository extends JpaRepository<RecipeProduct, Long> {
    List<RecipeProduct> findByRecipeId(Long recipeId);

    List<RecipeProduct> findByProductId(Long productId);
}