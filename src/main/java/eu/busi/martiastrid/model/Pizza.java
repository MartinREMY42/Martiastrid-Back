package eu.busi.martiastrid.model;

import java.io.Serializable;
import java.util.*;

public class Pizza implements Serializable {
    private Integer id;
    private String genericName;
    private Integer price;
    private List<Recipe> recipes;
    private Set<String> category;

    public Pizza() { }

    public Pizza(Integer id, String genericName, Integer price) {
        this.id = id;
        this.genericName = genericName;
        this.price = price;
    }

    public Pizza(Integer id, String genericName, Integer price, Collection<Recipe> recipes,
                 Set<String> category) {
        this.id = id;
        this.genericName = genericName;
        this.price = price;
        this.recipes = new ArrayList<>(recipes);
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

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
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
        if (pizza.genericName.equals("CustomPizza") &&
            this.genericName.equals("CustomPizza")) {
            // comparer deux pizza custom se fait à la liste d'ingrédient
            int size = pizza.recipes.size();
            if (size!=this.recipes.size()) {
                return false;
            }
            Recipe[] recipesPizza = new Recipe[size];
            recipesPizza = pizza.recipes.toArray(recipesPizza);
            Recipe[] recipesThis = new Recipe[size];
            recipesThis = this.recipes.toArray(recipesThis);
            return Arrays.equals(recipesPizza, recipesThis);
        }
        if (!pizza.genericName.equals("CustomPizza") &&
            !this.genericName.equals("CustomPizza")) {
            // comparer deux pizza standard se fait à l'id
            return pizza.id.equals(this.id);
        }
        return false; // une pizza custom et une standard sont différentes
        //return pizza.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return this.genericName.equals("CustomPizza") ? Objects.hash(recipes) : Objects.hash(id);
    }

    @Override
    public String toString() {
        return "";
    }
}
