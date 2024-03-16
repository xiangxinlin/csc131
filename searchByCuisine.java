package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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
                "https://api.spoonacular.com/recipes/complexSearch?apiKey=%s&cuisine=%s&number=10&addRecipeInformation=false",
                API_KEY, cuisine);

        // Create the HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .GET()
                .build();

        try {
            // Send the request and handle the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // "Parse" the response to extract recipe titles and image URLs
            List<String[]> recipes = extractRecipes(response.body());

            // Display the extracted titles and image URLs with numbering
            System.out.println("Recipes found:");
            for (int i = 0; i < recipes.size(); i++) {
                String[] recipe = recipes.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, recipe[0], recipe[1]); // Title - Image URL
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred while requesting recipes: " + e.getMessage());
        }
    }

    private static List<String[]> extractRecipes(String responseBody) {
        List<String[]> recipes = new ArrayList<>();
        String[] parts = responseBody.split("\\},\\{");
        for (String part : parts) {
            String title = extractValue(part, "title");
            String image = extractValue(part, "image");
            recipes.add(new String[]{title, image});
        }
        return recipes;
    }

    private static String extractValue(String data, String key) {
        String keyPattern = "\"" + key + "\":\"";
        int start = data.indexOf(keyPattern) + keyPattern.length();
        int end = data.indexOf("\"", start);
        if (start > keyPattern.length() - 1 && end > -1) {
            return data.substring(start, end);
        }
        return "Not found";
    }
}

