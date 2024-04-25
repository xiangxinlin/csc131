package com.example;

import java.util.Scanner;
import java.util.List;
import org.bson.Document;

public class viewRecipes {
    Scanner scanner = new Scanner(System.in);

    public void recipeDetails() {
        recipeSaver recipeSaver = new recipeSaver();
        List<Document> savedRecipes = recipeSaver.getSavedRecipes();

        if (!savedRecipes.isEmpty()) {
            System.out.println("Your list of saved recipes:");
            int index = 1;
            for (Document savedRecipe : savedRecipes) {
                String savedTitle = savedRecipe.getString("title");
                System.out.println(index + ": " + savedTitle);
                index++;
            }
        } else {
            System.out.println("No saved recipes found.");
            return;
        }

        System.out.println("Enter the number of a recipe for detailed view (or '0' to go back):");
        int optionNum;
        try {
            optionNum = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("ERROR: Invalid input. Please enter a valid number.");
            return;
        }

        if (optionNum >= 1 && optionNum <= savedRecipes.size()) {
            Document recipeDetails = savedRecipes.get(optionNum - 1);

            // Check if all required fields are present
            if (recipeDetails.containsKey("title") && recipeDetails.containsKey("image") && recipeDetails.containsKey("id")
                    && recipeDetails.containsKey("imageType") && recipeDetails.containsKey("summary") && recipeDetails.containsKey("servings")
                    && recipeDetails.containsKey("spoonacularScore") && recipeDetails.containsKey("diets") && recipeDetails.containsKey("cuisines")
                    && recipeDetails.containsKey("ingredients") && recipeDetails.containsKey("instructions")) {

                String title = recipeDetails.getString("title");
                String image = recipeDetails.getString("image");
                int id = recipeDetails.getInteger("id");
                String imageType = recipeDetails.getString("imageType");
                String summary = recipeDetails.getString("summary");
                String servings = recipeDetails.getString("servings");
                float spoonacularScore = Float.parseFloat(recipeDetails.getString("spoonacularScore"));
                String diets = recipeDetails.getString("diets");
                String cuisines = recipeDetails.getString("cuisines");
                String ingredients = recipeDetails.getString("ingredients");
                String instructions = recipeDetails.getString("instructions");

                System.out.println("Title: " + title);
                System.out.println("Image: " + image);
                System.out.println("ID Number: " + id);
                System.out.println("Image Type: " + imageType);
                System.out.println("Summary: " + summary);
                System.out.println("Servings: " + servings);
                System.out.println("Rating: " + spoonacularScore);
                System.out.println("Diet(s): " + diets);
                System.out.println("Cuisine(s): " + cuisines);
                System.out.println("Ingredients: " + ingredients);
                System.out.println("Instructions: " + instructions);
            } else {
                System.out.println("ERROR: Required fields are missing in the saved recipe document.");
            }
        } else if (optionNum == 0) {
            System.out.println("Returning...");
        } else {
            System.out.println("ERROR: Invalid choice selected");
        }
    }
}
