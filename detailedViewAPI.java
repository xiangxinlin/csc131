package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class detailedViewAPI {
    private static final String API_KEY = "42c073de1b0e477089808c29c9c27139"; // API Key included as requested

    @SuppressWarnings("static-access")
	public void viewAPIDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter recipe ID:");
        String id = scanner.nextLine().trim();

        try {
            int recipeId = Integer.parseInt(id);
            String requestURL = String.format(
                    "https://api.spoonacular.com/recipes/" + id + "/information?apiKey=%s&includeNutrition=false&number=10", API_KEY);
            //String requestURL1 = String.format("https://api.spoonacular.com/recipes/" + id + "/analyzedInstructions", API_KEY);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestURL))
                    .GET()
                    .build();
            /*
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create(requestURL1))
                    .GET()
                    .build();
            */

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                //HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
                
                    String responseBody = response.body();
                    //String responseBody1 = response1.body();
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
        System.out.println("____________________");
        //Title
        System.out.println("\nTitle: ");
        String[] titlePart = jsonData.split("\"title\":\"");
        for (int i = 1; i < titlePart.length; i++) {
            String title = titlePart[i].split("\"", 2)[0];
            System.out.print(title);
        }
        //Summary
        System.out.println("\n\nSummary: ");
        String[] summaryPart = jsonData.split("\"summary\":\"");
        for (int i = 1; i < summaryPart.length; i++) {
            String summary = summaryPart[i].split("\"", 2)[0];
            System.out.print(summary);
        }
        //Ingredients
        System.out.println("\n\nIngredients: ");
        String[] ingredientPart = jsonData.split("\"name\":\"");
        for (int i = 1; i < ingredientPart.length; i++) {
            String ingredient = ingredientPart[i].split("\"", 2)[0];
            System.out.print(ingredient + ", ");
        }
        ///*
        System.out.println("\n\nInstructions: ");
        String[] instructionsPart = jsonData.split("\"step\":\"");
        int count = 1;
        for (int i = 1; i < instructionsPart.length; i++) {
            String instructions = instructionsPart[i].split("\"", 2)[0];
            System.out.println(count++ + ". " + instructions);
        }
        //*/
        //Servings
        String[] parts = jsonData.split(",");
        for (String part : parts) {
            // Look for the part containing the servings information
            if (part.contains("\"servings\"")) {
                // Split the part containing servings by :
                String[] servingPart = part.split(":");
                // Get the serving value
                String servingsValue = servingPart[1].trim();
                // Print the servings
                System.out.println("\n\nServings: " + servingsValue);
                // Break the loop since we found the servings information
                break;
            }
        }
        //Time
        for (String part : parts) {
            // Look for the part containing the time information
            if (part.contains("\"readyInMinutes\"")) {
                // Split the part containing time :
                String[] timePart = part.split(":");
                // Get the time value
                String timeValue = timePart[1].trim();
                // Print the time in minutes
                System.out.println("\nTime: " + timeValue + " minutes");
                // Break the loop since we found the time information
                break;
            }
        }
        //Dish Types
        for (String part : parts) {
            // Look for the part containing the dish types information
            if (part.contains("\"dishTypes\"")) {
                // Split the part containing dish types by ":"
                String[] dishTypesPart = part.split(":");
                // Get the dish types value
                String dishTypesValue = dishTypesPart[1].trim();
                // Print the dish types
                System.out.println("\nDish Types: " + dishTypesValue);
                // Break the loop since we found the dish types information
                break;
            }
        }
    }
}
