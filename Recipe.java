public class Recipe {
    private String name;
    private String ingredients;
    private String instructions;
    private DietType dietType;

    // Constructors
    public Recipe(String name, String ingredients, String instructions, DietType dietType) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.dietType = dietType;
    }

    // Default constructor
    public Recipe() {
        // Initialize default values or leave fields empty
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }
      @Override
        public String toString() {
            return "Recipe{" +
                    "title='" + title + '\'' +
                    ", ingredients='" + ingredients + '\'' +
                    ", instructions='" + instructions + '\'' +
                    ", dietType=" + dietType +
                    '}';

    }
}
