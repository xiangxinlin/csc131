package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class searchByCuisine{

    private static final String API_KEY = "41c2b73f2580458ea8e845483f07dbee"; // API Key included as requested

    public void searchCuisine() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the cuisine type (e.g., Italian, Indian):");
        String cuisine = scanner.nextLine().trim();

        // Construct the request URL
        String requestURL = String.format(
                "https://api.spoonacular.com/recipes/complexSearch?apiKey=%s&cuisine=%s&number=10&addRecipeInformation=true&addRecipeInstructions=true&fillIngredients=true",
                API_KEY, cuisine);

        // Create the HTTP client and request
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
                    System.out.println("Please enter the correct cuisine.");
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred while requesting recipes: " + e.getMessage());
        }
    }
}
