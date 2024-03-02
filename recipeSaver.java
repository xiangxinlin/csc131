package com.example;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

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
    public List<Document> getSavedRecipes() {
        List<Document> savedRecipes = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            // Query the collection to get all saved recipes
            FindIterable<Document> result = collection.find();

            // Iterate over the result and add each document to the list
            MongoCursor<Document> cursor = result.iterator();
            while (cursor.hasNext()) {
                savedRecipes.add(cursor.next());
            }
        } catch (Exception e) {
            System.err.println("Error retrieving saved recipes from MongoDB: " + e.getMessage());
        }
        return savedRecipes;
    }
}

