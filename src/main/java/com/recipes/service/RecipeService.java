package com.recipes.service;

import java.util.List;

import com.recipes.dto.response.RecipeResponseDto;
import com.recipes.entity.Recipe;

public interface RecipeService {
	public Recipe findById(Long id);

	public List<RecipeResponseDto> getAllRecipes();
	
	public List<RecipeResponseDto> getAllRecipes(Integer pageNumber, Integer pageSize);
}