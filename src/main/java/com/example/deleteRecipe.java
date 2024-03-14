package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Scanner;

public class deleteRecipe {
    // MongoDB connection URI
    private static final String MONGODB_URI = "mongodb+srv://nick:csus@csc131.tct5wqu.mongodb.net/";

    public static void main(String[] args) {
        // Use scanner for user input
        try (Scanner scanner = new Scanner(System.in)) {
            // Prompt user to enter the title of the recipe to delete
            System.out.println("Enter the title of the recipe you wish to delete:");
            String title = scanner.nextLine();

            // Call method to delete the specified recipe
            deleteDocument(title);
        }
    }

    // Method to delete a recipe document from MongoDB
    private static void deleteDocument(String title) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            // Connect to MongoDB and access the specified database and collection
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            // Attempt to delete the document with the given title
            if (collection.deleteOne(new Document("title", title)).getDeletedCount() > 0) {
                // Print success message if document was deleted
                System.out.println("Recipe deleted successfully.");
            } else {
                // Print message if no matching document was found
                System.out.println("No matching recipe found to delete.");
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the deletion process
            System.err.println("An error occurred while deleting the recipe: " + e.getMessage());
        }
    }
}
