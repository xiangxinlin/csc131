package com.example;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class recipeSaver {
    // MongoDB connection string
    private static final String MONGODB_URI = "mongodb+srv://nick:csus@csc131.tct5wqu.mongodb.net/";

    // Method to save a recipe to the MongoDB database
    public void saveRecipe(int id, String title, String imageUrl, String imageType, boolean saveConfirmed) {
        if (!saveConfirmed) {
            System.out.println("Recipe not saved. User declined to save.");
            return;
        }

        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            // Create a document to represent the recipe
            Document recipeDoc = new Document("id", id)
                                    .append("title", title)
                                    .append("image", imageUrl)
                                    .append("imageType", imageType);

            // Insert the document into the collection
            collection.insertOne(recipeDoc);

            System.out.println("Recipe saved: " + title);
        } catch (Exception e) {
            System.err.println("Error saving recipe to MongoDB: " + e.getMessage());
        }
    }
}

