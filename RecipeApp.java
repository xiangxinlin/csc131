import java.util.List;
import java.util.Scanner;

public class RecipeApp {

    public static void main(String[] args) {
        RecipeApp recipeApp = new RecipeApp();
        recipeApp.run();
    }

    public void run() {
        List<Recipe> allRecipes = loadRecipesFromSpoonacular();

        while (true) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    searchByCuisine(allRecipes);
                    break;
                case 2:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchByCuisine(List<Recipe> allRecipes) {
        System.out.print("Enter the cuisine type to search for (ITALIAN, MEXICAN, etc.): ");
        Scanner scanner = new Scanner(System.in);
        CuisineType selectedCuisine = CuisineType.valueOf(scanner.nextLine().trim().toUpperCase());

        RecipeService recipeService = new RecipeService();
        List<Recipe> matchingRecipes = recipeService.searchRecipesByCuisine(selectedCuisine);

        displayRecipes(matchingRecipes);
    }

    private void displayRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }

    private void displayMenu() {
        System.out.println("=== Recipe Application ===");
        System.out.println("1. Search recipes by cuisine");
        System.out.println("2. Exit");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume invalid input
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        return choice;
    }
}
