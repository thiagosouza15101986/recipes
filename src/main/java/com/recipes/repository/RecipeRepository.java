package com.recipes.repository;

import com.recipes.entity.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long>  {
}