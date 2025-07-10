package com.recipes;

import com.recipes.controller.RecipeController;
import com.recipes.dto.response.CartResponseDto;
import com.recipes.dto.response.RecipeResponseDto;
import com.recipes.entity.Cart;
import com.recipes.entity.CartRecipeItem;
import com.recipes.entity.Recipe;
import com.recipes.repository.CartRepository;
import com.recipes.repository.RecipeRepository;
import com.recipes.service.RecipeService;
import com.recipes.service.impl.CartServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {
	@InjectMocks
	private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private RecipeRepository recipeRepository;
    
    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	void shouldReturnAllRecipes() {
		List<RecipeResponseDto> dtos = List
				.of(RecipeResponseDto.builder().id(1L).name("Chocolate Cake").description("Classic cake").build());
		when(recipeService.getAllRecipes()).thenReturn(dtos);

		recipeController.getAllRecipes(null, null);
	}

    @Test
    void shouldReturnCartById() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setRecipeItems(new ArrayList<>());
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        CartResponseDto dto = cartService.getCartById(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
    }

    @Test
    void shouldAddRecipeToCart() {
    	var cart = Cart.builder()
        .id(1L)
        .recipeItems(new ArrayList<>())
        .productItems(new ArrayList<>())
        .totalInCents(0L)
        .build();

        var recipe = Recipe.builder()
        .id(2L)
        .priceInCents(100L)
        .build();
        
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(recipeService.findById(2L)).thenReturn(recipe);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CartResponseDto response = cartService.addRecipe(1L, 2L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(100L, response.getTotalInCents());

        // Supondo que seu DTO tem getRecipeItems()
        assertNotNull(response.getRecipeItems());
        assertEquals(1, response.getRecipeItems().size());

        var item = response.getRecipeItems().get(0);
        assertEquals(2L, item.getRecipeId());
        assertEquals(1, item.getQuantity());
    }

    @Test
    void shouldRemoveRecipeFromCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        Recipe recipe = new Recipe(2L, "Cake", "Chocolate cake", 1500l, null);
        CartRecipeItem item = new CartRecipeItem();
        item.setRecipeId(2L);
        item.setRecipe(recipe);
        item.setQuantity(1);
        cart.setRecipeItems(new ArrayList<>());
        cart.getRecipeItems().add(item);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any())).thenReturn(cart);

        CartResponseDto dto = cartService.removeRecipe(1L, 2L);

        assertNotNull(dto);
        assertTrue(dto.getRecipeItems().isEmpty());
    }
}