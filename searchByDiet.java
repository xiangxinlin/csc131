package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;

public class searchByDiet {

    private static final String API_KEY = "42c073de1b0e477089808c29c9c27139";
    private static final List<String> supportedDiets = Arrays.asList(
            "gluten-free", "ketogenic", "vegetarian", "lacto-vegetarian", 
            "ovo-vegetarian", "vegan", "pescetarian", "paleo", 
            "primal", "low-fodmap", "whole30");
    
    public void searchDiet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Here is a list of supported diets: Gluten-Free, Ketogenic, Vegetarian, Lacto-Vegetarian, Ovo-Vegetarian, Vegan, Pescetarian, Paleo, Primal, Low-FODMAP, Whole30.");
        System.out.println("Enter your diet:");
        String diet = scanner.nextLine().toLowerCase();

        //check user input
        if (!supportedDiets.contains(diet)) {
            System.out.println("The entered diet is not supported.");
            return; // Exit the method if diet is not supported
        }
        
        String requestURL = String.format(
                "https://api.spoonacular.com/recipes/complexSearch?apiKey=%s&diet=%s&addRecipeInformation=true&number=10",
                API_KEY, diet);

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
