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

        int page = 1;
        boolean continueSearch = true;
        while (continueSearch) {
            String cuisine = scanner.nextLine().trim();
            int resultsPerPage = 10;
            int offset = (page - 1) * resultsPerPage;
            String requestURL = String.format(
                    "https://api.spoonacular.com/recipes/complexSearch?apiKey=%s&cuisine=%s&number=%d&offset=%d&addRecipeInformation=true&addRecipeInstructions=true&fillIngredients=true&addRecipeNutrition=true",
                    API_KEY, cuisine, resultsPerPage, offset);

        // Create the HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .GET()
                .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String jsonResponse = response.body();
                    if (jsonResponse != null) {
                        List<String> recipes = recipeJsonParser.parseRecipes(jsonResponse);
                        if (!recipes.isEmpty()) {
                            recipeInteraction.handleRecipeSavingAndViewing(scanner, recipes.toArray(new String[0]), new recipeSaver());
                            System.out.println("\n\nDo you want to fetch more recipes? (yes/no)");
                            String answer = scanner.nextLine();
                            if ("yes".equalsIgnoreCase(answer)) {
                                page++;
                            } else {
                                continueSearch = false;
                            }
                        } else {
                            System.out.println("No recipes found matching your cuisine. Try a different cuisine.");
                            continueSearch = false;
                        }
                    }
                } else {
                    System.out.println("Failed to fetch recipes: HTTP error code : " + response.statusCode());
                    continueSearch = false;
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("An error occurred while requesting recipes: " + e.getMessage());
                continueSearch = false;
            }
        }
    }
}
