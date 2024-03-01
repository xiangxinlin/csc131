package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class spoonacularApiTesting {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your query: ");
        String findQuery = scan.nextLine();

        try {
            String apiKey = "41c2b73f2580458ea8e845483f07dbee";
            String urlString = "https://api.spoonacular.com/recipes/complexSearch?query=" + findQuery + "&apiKey=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder infoString = new StringBuilder();
                BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                while ((line = read.readLine()) != null) {
                    infoString.append(line);
                }
                read.close();

                String jsonResponse = infoString.toString();
                String[] recipes = jsonResponse.split("\"results\":\\[")[1].split("\\],\"offset\"")[0].split("\\},\\{");

                System.out.println("Recipes found:");
                int recipeNumber = 1;
                for (String recipe : recipes) {
                    String title = recipe.split("\"title\":\"")[1].split("\",\"")[0];
                    String image = recipe.split("\"image\":\"")[1].split("\",\"")[0];
                    System.out.println(recipeNumber + ". " + title + " - " + image);
                    recipeNumber++;
                }

                System.out.println("Enter the numbers of the recipes you want to save, separated by spaces, or enter 'none' to skip:");
                String input = scan.nextLine();

                if (!input.equalsIgnoreCase("none")) {
                    String[] selectedRecipeNumbers = input.split("\\s+");
                    for (String recipeNumberStr : selectedRecipeNumbers) {
                        int selectedRecipeNumber = Integer.parseInt(recipeNumberStr);
                        if (selectedRecipeNumber > 0 && selectedRecipeNumber <= recipes.length) {
                            String selectedRecipe = recipes[selectedRecipeNumber - 1];
                            String title = selectedRecipe.split("\"title\":\"")[1].split("\",\"")[0];
                            String image = selectedRecipe.split("\"image\":\"")[1].split("\",\"")[0];
                            String id = selectedRecipe.split("\"id\":")[1].split(",")[0];
                            String imageType = selectedRecipe.split("\"imageType\":\"")[1].split("\",\"")[0];

                            recipeSaver recipeSaver = new recipeSaver();
                            recipeSaver.saveRecipe(Integer.parseInt(id), title, image, imageType, true);
                            System.out.println("Recipe '" + title + "' saved successfully!");
                        } else {
                            System.out.println("Invalid recipe number: " + selectedRecipeNumber + ". Skipping...");
                        }
                    }
                } else {
                    System.out.println("No recipe saved.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scan.close();
        }
    }
}
