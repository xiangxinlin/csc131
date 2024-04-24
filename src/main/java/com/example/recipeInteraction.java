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
            // Split the input into an array of recipe numbers.
            String[] selectedRecipeNumbers = input.split("\\s+");
            for (String recipeNumberStr : selectedRecipeNumbers) {
                try {
                    int recipeNumber = Integer.parseInt(recipeNumberStr) - 1;
                    if (recipeNumber >= 0 && recipeNumber < recipes.length) {
                        String[] recipeDetails = recipes[recipeNumber].split(" - ");
                        int servings = Integer.parseInt(recipeDetails[2].split(" ")[0]); // "4 servings" -> ["4", "servings"]
                        int id = Integer.parseInt(recipeDetails[3].split(" ")[0]);
                        String imageType = (recipeDetails[4].split(" ")[0]);
                        String summary = cleanHtml(recipeDetails[5]);
                        String diets = (recipeDetails[6]);
                        String cuisines = (recipeDetails[7]);
                        float spoonacularScore = Float.parseFloat(recipeDetails[8].split(" ")[0]);
                        String dishTypes = (recipeDetails[9]);
                        String ingredients = (recipeDetails[10]);
                        String instructions = (recipeDetails[11]);
                        // Call method to save the recipe in the database.
                        recipeSaverInstance.saveRecipe(recipeDetails[0], recipeDetails[1], servings, id, imageType, summary, diets, cuisines, spoonacularScore, dishTypes, ingredients, instructions);
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
                        System.out.println(index + ": " + recipe.getString("title") + "\n   -" + recipe.getString("image") + "\n   -" + recipe.getInteger("servings") + " servings");
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
}
