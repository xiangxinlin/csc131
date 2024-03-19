//in progress needs to be updated as we go
package com.example;

import java.util.Scanner;

public class HomePage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the Recipe Management System");
        while (true) {
            System.out.println("\nProceed with your Commands: ");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by ID"); 
            System.out.println("3. Search by Ingredients");
            System.out.println("4. Search by Cuisine");
            System.out.println("5. Search by Diet");
            System.out.println("6. Update a Recipe");
            System.out.println("7. Delete a Recipe");
            System.out.println("8. Create a Recipe");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.println("\nSearching by Title...");
                    searchByTitle SearchByTitle = new searchByTitle();
                    SearchByTitle.searchTitle();
                    break;
                case 2:
                    System.out.println("\nSearching by ID...");
                    searchByID SearchByID = new searchByID();
                    SearchByID.searchID();
                    break;
                case 3:
                    System.out.println("\nSearching by Ingredients...");
                    searchByIngredients SearchByIngredients = new searchByIngredients();
                    SearchByIngredients.searchIngredients();
                    break;
                case 4:
                    System.out.println("\nSearching by Cuisine...");
                    searchByCuisine SearchByCuisine =  new searchByCuisine();
                    SearchByCuisine.searchCuisine();
                    break;
                case 5:
                    System.out.println("\nSearching by Diet...");
                    searchByDiet SearchByDiet = new searchByDiet();
                    SearchByDiet.searchDiet();
                    break;
                case 6:
                    System.out.println("\nUpdating a Recipe...");
                    updateRecipe UpdateRecipe = new updateRecipe();
                    UpdateRecipe.update();
                    break;
                case 7:
                    System.out.println("\nDeleting a Recipe...");
                    deleteRecipe DeleteRecipe = new deleteRecipe();
                    DeleteRecipe.delete();
                    break;
                case 8:
                    System.out.println("\nCreating a Recipe...");
                    recipeCreator RecipeCreator = new recipeCreator();
                    RecipeCreator.create();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return; // Exit the application
                default:
                    System.out.println("Invalid choice, please enter a number between 1 and 8.");
            }
        }
    }
}

