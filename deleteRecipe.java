package org.example;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import java.util.Scanner;

public class deleteRecipe {

    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://xiangxin:csc@csc131.tct5wqu.mongodb.net/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            Scanner id = new Scanner(System.in);
            System.out.println("Enter the recipe id for deletion: ");

            Bson query = eq("_id", id);

            try {
                // Deletes the document and displays it
                DeleteResult result = collection.deleteOne(query);
                //TODO: show the contents deleted
                System.out.println("Deleted document count: " + result.getDeletedCount());

                // Prints a message if any exceptions occur during the operation
            } catch (MongoException err) {
                System.err.println("Unable to delete due to an error: " + err);
            }
        }
    }
}