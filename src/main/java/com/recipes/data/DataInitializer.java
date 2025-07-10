package com.recipes.data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.recipes.entity.Product;
import com.recipes.entity.Recipe;
import com.recipes.entity.RecipeProduct;
import com.recipes.repository.ProductRepository;
import com.recipes.repository.RecipeProductRepository;
import com.recipes.repository.RecipeRepository;

@Configuration
public class DataInitializer {
	@Bean
	@Transactional
	CommandLineRunner initData(
			ProductRepository productRepository,
			RecipeRepository recipeRepository,
			RecipeProductRepository recipeProductRepository
	) {
		return args -> {
			// Products
			Product flour = productRepository.save(new Product(null, "Wheat Flour", 500l)); // $5.00
			Product egg = productRepository.save(new Product(null, "Egg", 100l)); // $1.00
			Product milk = productRepository.save(new Product(null, "Milk", 300l)); // $3.00
			Product sugar = productRepository.save(new Product(null, "Sugar", 200l)); // $2.00
			Product cocoa = productRepository.save(new Product(null, "Cocoa Powder", 400l)); // $4.00

			// Recipes
			Recipe chocolateCake = recipeRepository.save(new Recipe(null,
					"Chocolate Cake", 
					"Classic fluffy chocolate cake with frosting", 
					1500l, null)); // $15.00
			Recipe pancake = recipeRepository.save(new Recipe(null,
                "Simple Pancake", 
                "Quick and easy pancake recipe", 
                800l, null)); // $8.00

            // Recipe-Product associations (ingredients)
            recipeProductRepository.save(new RecipeProduct(null, chocolateCake, flour, 2.0d, "cups"));
            recipeProductRepository.save(new RecipeProduct(null, chocolateCake, egg, 3.0d, "units"));
            recipeProductRepository.save(new RecipeProduct(null, chocolateCake, milk, 1.0d, "cup"));
            recipeProductRepository.save(new RecipeProduct(null, chocolateCake, sugar, 1.5d, "cups"));
            recipeProductRepository.save(new RecipeProduct(null, chocolateCake, cocoa, 0.5d, "cup"));
            recipeProductRepository.save(new RecipeProduct(null, pancake, flour, 1.0d, "cup"));
            recipeProductRepository.save(new RecipeProduct(null, pancake, egg, 1.0d, "unit"));
            recipeProductRepository.save(new RecipeProduct(null, pancake, milk, 1.0d, "cup"));
        };
    }
}