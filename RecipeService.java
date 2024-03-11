import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RecipeService {

    private static final String SPOONACULAR_API_KEY = "d2245e4829814142bf0c1c3000b76578";
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/";

    public List<Recipe> searchRecipesByDiet(CuisineType dietType) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = BASE_URL + "complexSearch?diet=" + dietType.name().toLowerCase() + "&apiKey=" + SPOONACULAR_API_KEY;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return parseRecipesFromJson(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<Recipe>();
        }
    }

    private List<Recipe> parseRecipesFromJson(String jsonResponse) {
        List<Recipe> recipes = new ArrayList<Recipe>();
        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);

        if (jsonObject.has("results")) {
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");
            for (JsonElement result : resultsArray) {
                JsonObject resultObject = result.getAsJsonObject();
                String title = resultObject.get("title").getAsString();
                String ingredients = resultObject.get("extendedIngredients").toString();
                String instructions = resultObject.get("instructions").getAsString();
            }

private List<Recipe> parseRecipesFromJson(String jsonResponse, CuisineType cuisineType) {      

                recipes.add(new Recipe(title, ingredients, instructions, cuisineType));
            }
        }
return parseRecipesFromJson(response.body(), dietType);
        return recipes;
    }
}
