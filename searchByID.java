package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class searchByID {

    private static final String API_KEY = "41c2b73f2580458ea8e845483f07dbee"; // Your API key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the recipe you'd like to find:");
        String recipeId = scanner.nextLine().trim();

        fetchRecipeDetails(recipeId);
    }

    private static void fetchRecipeDetails(String recipeId) {
        String url = String.format("https://api.spoonacular.com/recipes/%s/information?apiKey=%s", recipeId, API_KEY);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                parseAndPrintRecipe(response.body());
            } else {
                System.out.println("Failed to fetch recipe details.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching recipe details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void parseAndPrintRecipe(String jsonResponse) {
        String title = extractValue(jsonResponse, "title");
        // Summary handling has been removed
        System.out.println("Title: " + title);
    }

    // A very basic method to extract a simple string value from a JSON-like response
    private static String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        if (startIndex == -1 + searchKey.length()) {
            return "Key not found";
        }
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}




