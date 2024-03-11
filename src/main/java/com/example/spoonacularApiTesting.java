package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.List;
import org.bson.Document;

//class currently responsible for interacting with the API to search for recipes
public class spoonacularApiTesting {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your query: ");
        String findQuery = scan.nextLine();

        try {
            //API key for accessing 
            String apiKey = "41c2b73f2580458ea8e845483f07dbee";
            //URL for querying the API
            String urlString = "https://api.spoonacular.com/recipes/complexSearch?query=" + findQuery + "&apiKey=" + apiKey;
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

                String line;
                while ((line = read.readLine()) != null) {
                    infoString.append(line);
                }
                read.close();

                //Extract recipe info from API response
                String jsonResponse = infoString.toString();
                String[] recipes = jsonResponse.split("\"results\":\\[")[1].split("\\],\"offset\"")[0].split("\\},\\{");

                //Display found recipes to user
                System.out.println("Recipes found:");
                int recipeNumber = 1;
                for (String recipe : recipes) {
                    String title = recipe.split("\"title\":\"")[1].split("\",\"")[0];
                    String image = recipe.split("\"image\":\"")[1].split("\",\"")[0];
                    System.out.println(recipeNumber + ". " + title + " - " + image);
                    recipeNumber++;
                }

                //ask if user wants to save recipes
                System.out.println("Enter the numbers of the recipes you want to save, separated by spaces, or enter 'none' to skip:");
                String input = scan.nextLine();

                //process if the user input for saving recipes
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
                            //save the selected recipe 
                            recipeSaver.saveRecipe(Integer.parseInt(id), title, image, imageType, true);
                            System.out.println("Recipe '" + title + "' saved successfully!");
                        } else {
                            System.out.println("Invalid recipe number: " + selectedRecipeNumber + ". Skipping...");
                        }
                    }
                } else {
                    System.out.println("No recipe saved.");
                }
                
                // Ask the user if they want to view their list of saved recipes
                System.out.println("Do you want to view your list of saved recipes? (yes/no)");
                String viewListInput = scan.nextLine();
                if (viewListInput.equalsIgnoreCase("yes")) {
                    //displays saved recipe if user typed yes
                    recipeSaver recipeSaver = new recipeSaver();
                    List<Document> savedRecipes = recipeSaver.getSavedRecipes();
                    if (!savedRecipes.isEmpty()) {
                        System.out.println("Your list of saved recipes:");
                        for (Document savedRecipe : savedRecipes) {
                            String savedTitle = savedRecipe.getString("title");
                            String savedImageUrl = savedRecipe.getString("image");
                            System.out.println(savedTitle + " - " + savedImageUrl);
                        }
                    } else {
                        System.out.println("No saved recipes found.");
                    }
                }

                //Ask the user if they want to delete a recipe
                System.out.println("Do you want to delete a recipe? (yes/no)");
                String deleteOption = scan.nextLine();
                if(deleteOption.equalsIgnoreCase("yes")){
                    recipeSaver recipeSaver = new recipeSaver();
                    List<Document> savedRecipes = recipeSaver.getSavedRecipes();
                    if(!savedRecipes.isEmpty()){
                        do{
                            System.out.println("Enter what category you would like to delete by:");
                            String field = scan.nextLine().trim();
                            System.out.println("Enter the value for this field:");
                            String value = scan.nextLine().trim();
                            deleteRecipe.deleteDocument(field, value);

                            System.out.println("Would you like to delete anything else? (yes/no)");
                        }while(scan.nextLine().equalsIgnoreCase("yes"));
                    }else{
                        System.out.println("No recipe available to delete.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scan.close();
        }
    }
}
