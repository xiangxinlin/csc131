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
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray results = jobject.getAsJsonArray("results");
        List<String> recipes = new ArrayList<>();

        if (results != null && results.size() > 0) {
            System.out.println("Recipes found:");
            int index = 1;
            for (JsonElement element : results) {
                JsonObject recipe = element.getAsJsonObject();
                String title = getStringSafe(recipe, "title");
                String image = getStringSafe(recipe, "image");
                int servings = getIntSafe(recipe, "servings");
                System.out.println(index + ". " + title + " - " + image + " - " + servings + " servings");
                recipes.add(title + " - " + image + " - " + servings);
                index++;
            }
        } else {
            System.out.println("No recipes found matching your query.");
        }
        return recipes;
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
            return element.getAsInt();
        }
        return 0; // Default value if not available
    }
}

