package com.example;

import java.util.Scanner;
import java.util.List;
import org.bson.Document;

public class viewRecipes{
    Scanner scanner = new Scanner(System.in);
    public void recipeView(){
        recipeSaver recipeSaver = new recipeSaver();
        List<Document> savedRecipes = recipeSaver.getSavedRecipes();
        if (!savedRecipes.isEmpty()) {
            System.out.println("Your list of saved recipes:");
            int index = 1;
            for (Document savedRecipe : savedRecipes) {
                String savedTitle = savedRecipe.getString("title");
                String savedImageUrl = savedRecipe.getString("image");
                System.out.println(index + ": " + savedTitle + " - " + savedImageUrl);
                index ++;
            }
        } else {
            System.out.println("No saved recipes found.");
        }

        System.out.println("Enter the number of a recipe for detailed view (or '0' to go back):");
        int optionNum = scanner.nextInt();
        if(optionNum >= 1 && optionNum <= savedRecipes.size()){
            Document recipeDetails = savedRecipes.get(optionNum - 1);
            String title = recipeDetails.getString("title");
            String imageUrl = recipeDetails.getString("image");
            String imageType = recipeDetails.getString("imageType");
            int id = recipeDetails.getInteger("id");

            System.out.println("Title: " + title);
            System.out.println("Image: " + imageUrl);
            System.out.println("Image Type: " + imageType);
            System.out.println("ID Number: " + id);
        }else if(optionNum == 0){
            System.out.println("Returning...");
        }else{
            System.out.println("ERROR: Invalid choice selected");
        }
    }
}
