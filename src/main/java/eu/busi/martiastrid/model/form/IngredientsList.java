package eu.busi.martiastrid.model.form;

import java.io.Serializable;
import java.util.Collection;

public class IngredientsList implements Serializable {
    private Collection<Integer> ingredients;
    private Integer quantity;

    public IngredientsList(Collection<Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientsList() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Collection<Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<Integer> ingredients) {
        this.ingredients = ingredients;
    }
}
