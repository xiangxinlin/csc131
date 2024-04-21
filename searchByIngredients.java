package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.List;

public class searchByIngredients {

    private static final String API_KEY = "41c2b73f2580458ea8e845483f07dbee"; // API Key included as requested

    public void searchIngredients() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ingredients separated by commas (e.g., tomato,egg):");
        String ingredients = scanner.nextLine().trim();

        String requestURL = String.format(
            "https://api.spoonacular.com/recipes/findByIngredients?apiKey=%s&ingredients=%s",
            API_KEY, ingredients);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            if (jsonResponse != null) {
                List<String> recipes = recipeJsonParser.parseRecipes(jsonResponse);
                if (!recipes.isEmpty()) {
                    recipeInteraction.handleRecipeSavingAndViewing(scanner, recipes.toArray(new String[0]), new recipeSaver());
                } else {
                    System.out.println("No recipes found matching your query.");
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred while requesting recipes: " + e.getMessage());
        }
    }
}

