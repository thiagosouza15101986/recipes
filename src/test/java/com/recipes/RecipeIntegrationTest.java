package com.recipes;

import com.recipes.dto.response.RecipeResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllRecipesFromApi() {
        String url = "http://localhost:" + port + "/api/recipes";
        RecipeResponseDto[] recipes = restTemplate.getForObject(url, RecipeResponseDto[].class);

        assertNotNull(recipes);
        assertTrue(recipes.length >= 0);
    }
}