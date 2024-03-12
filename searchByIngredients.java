package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class searchByIngredients {

    private static final String API_KEY = "41c2b73f2580458ea8e845483f07dbee"; // API Key included as requested

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ingredients separated by commas (e.g., tomato,egg):");
        String ingredients = scanner.nextLine().trim();

        String requestURL = String.format(
                "https://api.spoonacular.com/recipes/complexSearch?apiKey=%s&includeIngredients=%s&addRecipeInformation=true&number=10",
                API_KEY, ingredients);

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

