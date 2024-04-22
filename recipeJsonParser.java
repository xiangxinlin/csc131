package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class recipeJsonParser {
    public static List<String> parseRecipes(String jsonResponse) {
        JsonElement jelement = JsonParser.parseString(jsonResponse);
        List<String> recipes = new ArrayList<>();
        
        // Determine if the response is an object or an array and process accordingly
        if (jelement.isJsonObject()) {
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray results = jobject.getAsJsonArray("results");  // Used if the response is an object containing an array
            processJsonArray(recipes, results);
        } else if (jelement.isJsonArray()) {
            JsonArray jsonArray = jelement.getAsJsonArray();
            processJsonArray(recipes, jsonArray);
        } else {
            System.out.println("No recipes found matching your query.");
        }
        return recipes;
    }

    //Processes a JSON array containing recipe data
    private static void processJsonArray(List<String> recipes, JsonArray jsonArray) {
        if (jsonArray != null && jsonArray.size() > 0) {
            System.out.println("\nRecipes found:");
            System.out.println("--------------");
            int index = 1;
            for (JsonElement element : jsonArray) {
                JsonObject recipe = element.getAsJsonObject();
                String title = getStringSafe(recipe, "title");
                String image = getStringSafe(recipe, "image");
                int servings = getIntSafe(recipe, "servings");
                System.out.println(index + ". " + title + "\n   -" + image + "\n   -" + servings + " servings");
                System.out.println("------------------------------------------------------------------");
                recipes.add(title + " - " + image + " - " + servings);
                index++;
            }
        } else {
            System.out.println("No recipes found.");
        }
    }

    private static String getStringSafe(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        if (element != null && !element.isJsonNull()) {
            return element.getAsString();
        }
        return "Not available";
    }

    private static int getIntSafe(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        if (element != null && !element.isJsonNull()) {
            try {
                return element.getAsInt();
            } catch (NumberFormatException e) {
                return 0; // Default value if conversion fails
            }
        }
        return 0; // Default value if not available
    }
}

