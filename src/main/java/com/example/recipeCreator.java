package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class recipeCreator{
    private static final String MONGODB_URI = "mongodb+srv://nick:csus@csc131.tct5wqu.mongodb.net/";
    public void create(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the title of the recipe you'd like to create:");
            String title = scanner.nextLine();
            System.out.println("Enter the ingredients separated by commas:");
            String ingredients = scanner.nextLine();
            System.out.println("Enter the instructions:");
            String instructions = scanner.nextLine();

            createRecipe(title, ingredients, instructions);
        }catch(Exception e){
            System.err.println("An error occurred while processing the input: " + e.getMessage());
        }
    }

    public void createRecipe(String title, String ingredients, String instructions){

        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            // Connect to MongoDB and access the specified database and collection
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            // Create a new document
            Document document = new Document();
            document.append("title", title).append("ingredients", ingredients).append("instructions", instructions);
            // Insert the document into the collection
            collection.insertOne(document);

            System.out.println("Recipe created successfully.");

        } catch (Exception e) {
            // Handle any exceptions that occur during the update process
            System.err.println("An error occurred while creating the recipe: " + e.getMessage());
        }
    }
}
