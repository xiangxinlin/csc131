package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

//class currently responsible for interacting with the API to search for recipes
public class searchByTitle {
    public void searchTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your query: ");
        String findQuery = scanner.nextLine();

        try {
            //API key for accessing 
            String apiKey = "41c2b73f2580458ea8e845483f07dbee";
            //URL for querying the API
            String urlString = "https://api.spoonacular.com/recipes/complexSearch?query=" + findQuery + "&apiKey=" + apiKey + "&addRecipeInformation=true";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //checks HTTP response
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                //read and process API response
                StringBuilder infoString = new StringBuilder();
                BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String data;
                while ((data = read.readLine()) != null) {
                    infoString.append(data);
                }
                read.close();

                //Extract recipe info from API response
                String jsonResponse = infoString.toString();
                if (jsonResponse.contains("\"results\":[]")) {
                    System.out.println("No recipes found matching your query.");
                }
                String[] recipes = jsonResponse.split("\"results\":\\[")[1].split("\\],\"offset\"")[0].split("\\},\\{");
                // Display found recipes to user
                System.out.println("Recipes found:");
                int recipeNumber = 1;
                for (String recipe : recipes) {
                    String title = recipe.split("\"title\":\"")[1].split("\",\"")[0];
                    String image = recipe.split("\"image\":\"")[1].split("\",\"")[0];
                    System.out.println(recipeNumber + ". " + title + " - " + image);
                    recipeNumber++;
                }

                // Refactored to use RecipeInteraction
                recipeSaver recipeSaverInstance = new recipeSaver();
                recipeInteraction.handleRecipeSavingAndViewing(scanner, recipes, recipeSaverInstance);
            }
        } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
