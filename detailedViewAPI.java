package com.example;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
import java.net.URI;
//import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
//import java.util.List;
//import org.bson.Document;

//class currently responsible for interacting with the API to search for recipes
public class detailedViewAPI {
	private static final String API_KEY = "42c073de1b0e477089808c29c9c27139"; // API Key included as requested

    public void viewAPIDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter recipe id:");
        String id = scanner.nextLine().trim();

        String requestURL = String.format(
                "https://api.spoonacular.com/recipes/" + id + "/information?apiKey=%s&includeNutrition=false&number=10",
                API_KEY, id);
        
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