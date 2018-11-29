package eu.busi.martiastrid.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class Pizza implements Serializable {
    private Integer id;
    private String genericName;
    private Integer price;
    private ArrayList<Ingredient> ingredients ;
    private Boolean custom;
    private Set<String> category;

    public Pizza() {
    }

    public Pizza(Integer id, String genericName, Integer price, Collection<Ingredient> ingredients,
                 Boolean custom, Set<String> category) {
        this.id = id;
        this.genericName = genericName;
        this.price = price;
        this.ingredients = new ArrayList<>(ingredients);
        this.custom = custom;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean isCustom() {
        return custom;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<String> getCategory() {
        return category;
    }

    public void setCategory(Set<String> category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        if (pizza.custom && this.custom) {
            int size = pizza.ingredients.size();
            if (size!=this.ingredients.size()) {
                return false;
            }
            Ingredient[] ingredientsPizza = new Ingredient[size];
            ingredientsPizza = pizza.ingredients.toArray(ingredientsPizza);
            Ingredient[] ingredientsThis = new Ingredient[size];
            ingredientsThis = this.ingredients.toArray(ingredientsThis);
            return Arrays.equals(ingredientsPizza, ingredientsThis);
        }
        return Objects.equals(pizza.id, this.id);
        //return pizza.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return custom ? Objects.hash(ingredients) : Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", genericName='" + genericName + '\'' +
                ", price=" + price +
                ", ingredients=" + (ingredients==null ? "null" : ingredients.toString())+
                ", custom=" + custom +
                '}';
    }
}
