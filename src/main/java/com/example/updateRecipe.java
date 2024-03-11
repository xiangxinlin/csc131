package com.example;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

public class updateRecipe {

    public static void updateDocument(String title, String field, String value) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://xiangxin:csc@csc131.tct5wqu.mongodb.net/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("recipeProject");
            MongoCollection<Document> collection = database.getCollection("spoonacularRecipes");

            //Searches for a recipe with the provided title
            Document query = new Document().append("title", title);

            // Creates instructions to update the values of given field
            Document updates = new Document("$set", new Document(field, value));

            // Instructs the driver to insert a new document if none match the query
            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                // Updates the first document that has a "title" value of the provided title
                UpdateResult result = collection.updateOne(query, updates, options);

                // Prints the number of updated documents and the upserted document id, if an upsert was performed
                System.out.println("Modified document count: " + result.getModifiedCount());
                System.out.println("Upserted id: " + result.getUpsertedId());

                // Prints a message if any exceptions occur during the operation
            } catch (MongoException me) {
                System.err.println("Unable to update due to an error: " + me);
            }
        }
    }
}
