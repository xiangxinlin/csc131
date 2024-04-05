//currently displays title, summary, ingredients when searched
//need to include instructions

package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class detailedViewAPI {
    private static final String API_KEY = "42c073de1b0e477089808c29c9c27139"; // API Key included as requested

    public void viewAPIDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter recipe ID:");
        String id = scanner.nextLine().trim();

        try {
            int recipeId = Integer.parseInt(id);
            String requestURL = String.format(
                    "https://api.spoonacular.com/recipes/" + id
                            + "/information?apiKey=%s&includeNutrition=false&number=10",
                    API_KEY, id);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestURL))
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                printRecipeDetails(responseBody);
            } catch (IOException | InterruptedException e) {
                System.err.println("An error occurred while requesting recipes: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            // If the input is not a valid integer, print an error message and return
            System.err.println("The recipe ID must be a numerical value. Please try again.");
        }
    }
        

    private static void printRecipeDetails(String jsonData) {
        System.out.println("\nRecipe Details:");
        System.out.println("\nTitle: ");
        String[] parts = jsonData.split("\"title\":\"");
        for (int i = 1; i < parts.length; i++) {
            String title = parts[i].split("\"", 2)[0];
            System.out.print(title);
        }
        System.out.println("\nSummary: ");
        String[] parts2 = jsonData.split("\"summary\":\"");
        for (int i = 1; i < parts2.length; i++) {
            String summary = parts2[i].split("\"", 2)[0];
            System.out.print(summary);
        }
        System.out.println("\nIngredients: ");
        String[] parts1 = jsonData.split("\"name\":\"");
        for (int i = 1; i < parts1.length; i++) {
            String ingredient = parts1[i].split("\"", 2)[0];
            System.out.print(ingredient + ", ");
        }
    }
}
