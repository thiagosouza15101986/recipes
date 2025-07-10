package com.recipes.service.impl;

import com.recipes.dto.response.RecipeResponseDto;
import com.recipes.entity.Recipe;
import com.recipes.exception.ResourceNotFoundException;
import com.recipes.repository.RecipeRepository;
import com.recipes.service.RecipeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
	@Autowired
	private RecipeRepository recipeRepository;

	@Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
    }

	@Override
    public List<RecipeResponseDto> getAllRecipes() {
        return getAllRecipes(null, null);
    }

	@Override
    public List<RecipeResponseDto> getAllRecipes(Integer pageNumber, Integer pageSize) {
		List<Recipe> recipes = null;
		if (pageNumber != null && pageSize != null) {
			recipes = recipeRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    	} else {
    		recipes = recipeRepository.findAll();
    	}
        return extractListToDto(recipes);
    }

	private List<RecipeResponseDto> extractListToDto(List<Recipe> recipes) {
		return recipes.stream()
                .map(recipe -> RecipeResponseDto.builder()
                		.id(recipe.getId())
                        .name(recipe.getName())
                        .description(recipe.getDescription())
                        .build())
                    .toList();
	}
}