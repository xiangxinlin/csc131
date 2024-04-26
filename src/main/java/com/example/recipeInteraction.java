package com.example;

import java.util.Scanner;
import org.bson.Document;
import java.util.List;

public class recipeInteraction {

    // Handles both saving and viewing recipes based on user input.
public static void handleRecipeSavingAndViewing(Scanner scanner, String[] recipes, recipeSaver recipeSaverInstance) {
    System.out.println("\n\nEnter the numbers of the recipes you want to save, separated by spaces, or enter 'none' to skip:");
    String input = scanner.nextLine();

    if (!input.equalsIgnoreCase("none")) {
        String[] selectedRecipeNumbers = input.split("\\s+");
        for (String recipeNumberStr : selectedRecipeNumbers) {
            try {
                int recipeNumber = Integer.parseInt(recipeNumberStr) - 1;
                if (recipeNumber >= 0 && recipeNumber < recipes.length) {
                    String[] recipeDetails = recipes[recipeNumber].split(" - ");
                    String title = recipeDetails.length > 0 ? recipeDetails[0] : "Title not available";
                    String image = recipeDetails.length > 1 ? recipeDetails[1] : "Image not available";
                    int servings = recipeDetails.length > 2 ? Integer.parseInt(recipeDetails[2].split(" ")[0]) : 0;
                    int id = recipeDetails.length > 3 ? Integer.parseInt(recipeDetails[3]) : -1;
                    String imageType = recipeDetails.length > 4 ? recipeDetails[4] : "No image type";
                    String summary = recipeDetails.length > 5 ? cleanHtml(recipeDetails[5]) : "No summary";
                    String diets = recipeDetails.length > 6 ? recipeDetails[6] : "No diets";
                    String cuisines = recipeDetails.length > 7 ? recipeDetails[7] : "No cuisines";
                    float spoonacularScore = recipeDetails.length > 8 ? Float.parseFloat(recipeDetails[8]) : 0.0f;
                    String dishTypes = recipeDetails.length > 9 ? recipeDetails[9] : "No dish types";
                    String ingredients = recipeDetails.length > 10 ? recipeDetails[10] : "No ingredients";
                    String instructions = recipeDetails.length > 11 ? recipeDetails[11] : "No instructions";
                    String nutrition = recipeDetails.length > 12 ? recipeDetails[12] : "No nutrition";
                    int readyInMinutes = recipeDetails.length > 13 ? Integer.parseInt(recipeDetails[13]) : 0;

                    // Call method to save the recipe in the database
                    recipeSaverInstance.saveRecipe(title, image, servings, id, imageType, summary, diets, cuisines, spoonacularScore, dishTypes, ingredients, instructions, nutrition, readyInMinutes);
                } else {
                    System.out.println("Invalid recipe number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

        // Add a loop to handle user input for viewing saved recipes
        boolean validInputReceived = false;
        while (!validInputReceived) {
            System.out.println("\n\nDo you want to view your saved recipes? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
                validInputReceived = true;
                List<Document> savedRecipes = recipeSaverInstance.getSavedRecipes();
                if (savedRecipes.isEmpty()) {
                    System.out.println("No saved recipes found.");
                } else {
                    System.out.println("\nSaved Recipes:");
                    System.out.println("------------------------------------------------------------------");
                    int index = 1;
                    for (Document recipe : savedRecipes) {
                        String title = recipe.getString("title");
                        String image = recipe.getString("image");
                        int id = recipe.getInteger("id", -1);
                        String servingsFormatted = getFormattedServings(recipe);
                        String readyInMinutes = getFormattedReadyInMinutes(recipe);
                        System.out.println(index + ": " + title);
                        System.out.println("   -ID: " + id);  // Printing ID
                        System.out.println("   -Image: " + image);
                        System.out.println("   -Servings: " + servingsFormatted);
                        System.out.println("   -Ready in: " + readyInMinutes);
                        System.out.println("------------------------------------------------------------------");
                        index++;
                    }
                }
            } else if (response.equals("no")) {
                validInputReceived = true;
            } else {
                System.out.println("Invalid input, please enter 'yes' or 'no'.");
            }
        }
    }

    //method to clean html
    private static String cleanHtml(String htmlString){
        return htmlString.replaceAll("<[^>]*>", "");
    }

    // Helper method to format ready in minutes or return "N/A" if not applicable
    private static String getFormattedReadyInMinutes(Document recipe) {
        Integer minutes = recipe.getInteger("readyInMinutes");
        return (minutes != null && minutes > 0) ? minutes + " minutes" : "N/A";
    }
    
    //"N/A" if servings is 0
    private static String getFormattedServings(Document recipe) {
        Integer servings = recipe.getInteger("servings");
        return (servings != null && servings > 0) ? servings + " servings" : "N/A";
    }
}
