package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Scanner;

public class deleteRecipe {

    private static final String DATABASE_NAME = "recipeProject";
    private static final String COLLECTION_NAME = "spoonacularRecipes";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter what you would like to delete by:");
            String key = scanner.nextLine().trim();
            System.out.println("Enter the value for this field:");
            String value = scanner.nextLine().trim();
            deleteDocument(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteDocument(String key, String value) {
        String uri = "mongodb+srv://xiangxin:csc@csc131.tct5wqu.mongodb.net/";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Create a query to find the document based on key and value
            Document query = new Document(key, value);

            // Delete the document that matches the query
            collection.deleteOne(query);

            System.out.println("Document deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
