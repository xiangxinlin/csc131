package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class searchByTitle {
    private static final String API_KEY = "41c2b73f2580458ea8e845483f07dbee";  

    public void searchTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your query: ");
        String findQuery = scanner.nextLine();

        String jsonResponse = fetchRecipes(findQuery);
        if (jsonResponse != null) {
            List<String> recipes = recipeJsonParser.parseRecipes(jsonResponse);
            if (!recipes.isEmpty()) {
                recipeInteraction.handleRecipeSavingAndViewing(scanner, recipes.toArray(new String[0]), new recipeSaver());
            } else {
                System.out.println("No recipes found to save.");
            }
        }
    }

    private String fetchRecipes(String query) {
        String urlString = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=" + API_KEY + "&addRecipeInformation=true&fillIngredients=true&addRecipeInstructions=true";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Failed to fetch recipes: HTTP error code : " + responseCode);
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            System.err.println("An error occurred while searching for recipes: " + e.getMessage());
            return null;
        }
    }
}