package eu.busi.martiastrid.model;

import java.util.Collection;
import java.util.Objects;

public class RecipesQuantity {
    private Collection<Recipe> recipes;
    private int quantity;

    public RecipesQuantity() {
    }

    public RecipesQuantity(Collection<Recipe> recipes, int quantity) {
        this.recipes = recipes;
        this.quantity = quantity;
    }

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipesQuantity that = (RecipesQuantity) o;
        return quantity == that.quantity &&
                Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes, quantity);
    }
}
