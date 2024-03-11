package com.example;

import java.util.List;

public class Recipes {
	String title;
	int id;
	List<String> ingredients;
	String instructions;
	String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
        this.title = title;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
        this.id = id;
    }
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
        this.description = description;
    }
	
	public String toString() {
        return "Recipe [\n  title=" + title + ",\n  id=" + id + ",\n  Description=" + description + ",\n  Ingredients=" + id + "\n  Instrctions=" + instructions + "\n]";
    }
}