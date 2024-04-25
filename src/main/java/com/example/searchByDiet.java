package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class searchByDiet {

    private static final String API_KEY = "42c073de1b0e477089808c29c9c27139"; // API Key included as requested
    private static final List<String> supportedDiets = Arrays.asList(
            "gluten-free", "ketogenic", "vegetarian", "lacto-vegetarian", 
            "ovo-vegetarian", "vegan", "pescetarian", "paleo", 
            "primal", "low-fodmap", "whole30");
    
    public void searchDiet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Here is a list of supported diets: \n- Gluten-Free \n- Ketogenic \n- Vegetarian \n- Lacto-Vegetarian \n- Ovo-Vegetarian \n- Vegan \n- Pescetarian \n- Paleo \n- Primal \n- Low-FODMAP \n- Whole30");
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
            String responseBody = response.body();
            printRecipeTitles(responseBody);
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred while requesting recipes: " + e.getMessage());
        }
    }

    private static void printRecipeTitles(String jsonData) {
        System.out.println("\nList of Recipes:");
        String[] parts = jsonData.split("\"title\":\"");
        int count = 1;
        for (int i = 1; i < parts.length; i++) {
            String title = parts[i].split("\"", 2)[0];
            System.out.println(count++ + ". " + title);
        }
    }
}
