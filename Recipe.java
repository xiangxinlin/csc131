public class Recipe {
    private String title;
    private String ingredients;
    private String instructions;
    private CuisineType cuisineType;

    public Recipe(String title, String ingredients, String instructions, CuisineType cuisineType) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cuisineType = cuisineType;
    }

    // Getters, setters, and other methods as needed

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                ", cuisineType=" + cuisineType +
                '}';
    }
}
