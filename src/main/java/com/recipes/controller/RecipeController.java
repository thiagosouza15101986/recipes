package com.recipes.controller;

import com.recipes.dto.response.RecipeResponseDto;
import com.recipes.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
	@Autowired
    private RecipeService recipeService;

    @GetMapping
    // Lists the recipes available on the database 
    public ResponseEntity<List<RecipeResponseDto>> getAllRecipes(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
		return ResponseEntity.ok(recipeService.getAllRecipes(pageNumber, pageSize));
    }
}