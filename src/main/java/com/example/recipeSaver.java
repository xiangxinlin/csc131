package com.example;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import java.util.ArrayList;

public class recipeSaver {
    private static final String MONGODB_URI = "mongodb+srv://nick:csus@csc131.tct5wqu.mongodb.net/";

    public void saveRecipe(String title, String imageUrl, int servings, int id, String imageType, String summary, String diets, String cuisines, float spoonacularScore, String dishTypes, String ingredients, String instructions, int readyInMinutes) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            Document recipe = new Document("title", title)
                                .append("image", imageUrl)
                                .append("servings", servings)
                                .append("id", id)
                                .append("imageType", imageType)
                                .append("summary", summary)
                                .append("diets", diets)
                                .append("cuisines", cuisines)
                                .append("spoonacularScore", spoonacularScore)
                                .append("dishTypes", dishTypes)
                                .append("ingredients", ingredients)
                                .append("instructions", instructions)
                                .append("readyInMinutes", readyInMinutes);
            collection.insertOne(recipe);
            System.out.println("Recipe saved: " + title + " with " + servings + " servings");
        } catch (Exception e) {
            System.err.println("Error saving recipe: " + e.getMessage());
        }
    }

    public List<Document> getSavedRecipes() {
        List<Document> savedRecipes = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            for (Document recipe : collection.find()) {
                savedRecipes.add(recipe);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving recipes: " + e.getMessage());
        }
        return savedRecipes;
    }
}
