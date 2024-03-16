package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class searchByDiet {

    private static final String API_KEY = "42c073de1b0e477089808c29c9c27139"; // API Key included as requested

    public void searchDiet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Here is a list of supported diets: Gluten-Free, Ketogenic, Vegetarian, Lacto-Vegetarian, Ovo-Vegetarian, Vegan, Pescetarian, Paleo, Primal, Low-FODMAP, Whole30.");
        System.out.println("Enter your diet:");
        String diet = scanner.nextLine().trim();

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
        System.out.println("List of Recipes:");
        String[] parts = jsonData.split("\"title\":\"");
        int count = 1;
        for (int i = 1; i < parts.length; i++) {
            String title = parts[i].split("\"", 2)[0];
            System.out.println(count++ + ". " + title);
        }
    }
}
