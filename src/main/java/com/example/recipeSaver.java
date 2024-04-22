package com.example;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

//class for saving recipes and retrieving recipes from database
public class recipeSaver {
    // MongoDB connection string
    private static final String MONGODB_URI = "mongodb+srv://nick:csus@csc131.tct5wqu.mongodb.net/";

    //saveRecipe used to save a recipe to the database
    public void saveRecipe(String title, String image, int id, String imageType, String summary) {

        // Strip HTML tags from the summary
        String cleanedSummary = Jsoup.clean(summary, Safelist.none());

        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            // Create a document to represent the recipe
            Document recipe = new Document("title", title)
                    .append("image", image)
                    .append("id", id)
                    .append("imageType", imageType)
                    .append("summary", cleanedSummary);


            // Insert the document into the collection
            collection.insertOne(recipe);

            System.out.println("Recipe saved: " + title);
        } catch (Exception e) {
            System.err.println("Error saving recipe to MongoDB: " + e.getMessage());
        }
    }
    //method to retrieve the list of saved recipes from the database
    public List<Document> getSavedRecipes() {
        List<Document> savedRecipes = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            for (Document recipe : collection.find()) {
                savedRecipes.add(recipe);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving saved recipes from MongoDB: " + e.getMessage());
        }
        return savedRecipes;
    }
}
