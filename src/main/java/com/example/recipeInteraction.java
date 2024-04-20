package com.example;

import java.util.List;
import java.util.Scanner;
import org.bson.Document;

public class recipeInteraction {

    public static void handleRecipeSavingAndViewing(Scanner scanner, String[] recipes, recipeSaver recipeSaverInstance) {
        System.out.println("Enter the numbers of the recipes you want to save, separated by spaces, or enter 'none' to skip:");
        String input = scanner.nextLine();

        if (!input.equalsIgnoreCase("none")) {
            String[] selectedRecipeNumbers = input.split("\\s+");
            for (String recipeNumberStr : selectedRecipeNumbers) {
                int selectedRecipeNumber;
                try {
                    selectedRecipeNumber = Integer.parseInt(recipeNumberStr);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + recipeNumberStr + ". Skipping...");
                    continue;
                }
                if (selectedRecipeNumber > 0 && selectedRecipeNumber <= recipes.length) {
                    String selectedRecipe = recipes[selectedRecipeNumber - 1];
                    String title = selectedRecipe.split("\"title\":\"")[1].split("\",\"")[0];
                    String image = selectedRecipe.split("\"image\":\"")[1].split("\",\"")[0];
                    String id = selectedRecipe.split("\"id\":")[1].split(",")[0];
                    String imageType = selectedRecipe.split("\"imageType\":\"")[1].split("\",\"")[0];

                    String servings = selectedRecipe.split("\"servings\":\"")[1].split("\",\"")[0];

                    // Save the selected recipe
                    recipeSaverInstance.saveRecipe(Integer.parseInt(id), title, image, imageType, Integer.parseInt(servings), true);
                    System.out.println("Recipe '" + title + "' saved successfully!");
                } else {
                    System.out.println("Invalid recipe number: " + selectedRecipeNumber + ". Skipping...");
                }
            }
        } else {
            System.out.println("No recipe saved.");
        }

        // Ask if the user wants to view their list of saved recipes
        System.out.println("Do you want to view your list of saved recipes? (yes/no)");
        String viewListInput = scanner.nextLine();
        if (viewListInput.equalsIgnoreCase("yes")) {
            List<Document> savedRecipes = recipeSaverInstance.getSavedRecipes();
            if (!savedRecipes.isEmpty()) {
                System.out.println("Your list of saved recipes:");
                int index = 1;
                for (Document savedRecipe : savedRecipes) {
                    String savedTitle = savedRecipe.getString("title");
                    String savedImageUrl = savedRecipe.getString("image");
                    System.out.println(index + ": " + savedTitle + " - " + savedImageUrl);
                    index++;
                }
            } else {
                System.out.println("No saved recipes found.");
            }
        }
    }
}

