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
            System.out.println("Enter the image address:");
            String image = scanner.nextLine();
            System.out.println("Enter the serving size:");
            int servings = scanner.nextInt();
            System.out.println("Enter the image type of the recipe image:");
            String imageType = scanner.next();
            System.out.println("Enter the summary:");
            String summary = scanner.next();
            System.out.println("Enter the recipe's diet type(s) separated by commas:");
            String diets = scanner.next();
            System.out.println("Enter the recipe's cuisine type(s) separated by commas:");
            String cuisines = scanner.next();
            System.out.println("Enter your rating for this recipe:");
            double rating = scanner.nextDouble();
            System.out.println("Enter the recipe's dish type(s):");
            String dishTypes = scanner.next();
            System.out.println("Enter the ingredients separated by commas:");
            String ingredients = scanner.next();
            System.out.println("Enter the instructions:");
            String instructions = scanner.next();
            System.out.println("Enter the nutritional values separated by commas:");
            String nutrition = scanner.next();
            System.out.println("Enter the ready time in minutes:");
            int readyInMinutes = scanner.nextInt();

            createRecipe(title, image, servings, imageType, summary, diets, cuisines, rating, dishTypes, ingredients, instructions, nutrition, readyInMinutes);
        }catch(Exception e){
            System.err.println("An error occurred while processing the input: " + e.getMessage());
        }
    }

    public void createRecipe(String title, String image, int servings, String imageType, String summary, String diets, String cuisines, double rating, String dishTypes, String ingredients, String instructions, String nutrition, int readyInMinutes){

        try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
            // Connect to MongoDB and access the specified database and collection
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            // Create a new document
            Document document = new Document();
            document.append("title", title).append("image", image).append("servings", servings).append("imageType", imageType)
                    .append("summary", summary).append("diets", diets).append("cuisines", cuisines).append("rating", rating)
                    .append("dishTypes", dishTypes).append("ingredients", ingredients).append("instructions", instructions)
                    .append("nutrition", nutrition).append("readyInMinutes", readyInMinutes);
            // Insert the document into the collection
            collection.insertOne(document);

            System.out.println("Recipe created successfully.");

        } catch (Exception e) {
            // Handle any exceptions that occur during the update process
            System.err.println("An error occurred while creating the recipe: " + e.getMessage());
        }
    }
}
