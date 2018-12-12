package eu.busi.martiastrid.model;

import java.util.Objects;

public class Recipe {
    private Integer id;
    private int quantity;
    private Ingredient ingredient;

    public Recipe() { }

    public Recipe(Integer id, int quantity, Ingredient ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return quantity == recipe.quantity &&
                Objects.equals(id, recipe.id) &&
                Objects.equals(ingredient, recipe.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, ingredient);
    }

    @Override
    public String toString() {
        return "[" + this.quantity + " " + this.ingredient.getGenericName() + ")";
    }
}