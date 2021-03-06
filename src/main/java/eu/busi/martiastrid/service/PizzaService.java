package eu.busi.martiastrid.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import eu.busi.martiastrid.dataAccess.dao.IngredientDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;

import eu.busi.martiastrid.dataAccess.dao.RecipeDao;
import eu.busi.martiastrid.exception.PizzaException;

import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Pizza;

import eu.busi.martiastrid.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static eu.busi.martiastrid.constants.Constants.MAX_AMOUNT_INGREDIENTS_CUSTOM_PIZZA;
import static eu.busi.martiastrid.constants.Constantsi18n.*;

@Service
public class PizzaService {

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private CategoryService categoryService;

    public Pizza createNonStandardPizza(String genericName, Collection<Recipe> recipes) throws PizzaException {

        if (Objects.isNull(recipes) || recipes.size() < 1) {
            throw new PizzaException(ERROR_NOT_ENOUGH_INGREDIENTS_SELECTED);
        }

        if (!genericName.equals("PizzaParty") && countTotalIngredient(recipes) > MAX_AMOUNT_INGREDIENTS_CUSTOM_PIZZA) {
            throw new PizzaException(ERROR_TOO_MUCH_INGREDIENTS_SELECTED);
        }

        Collection<Ingredient> allIngredients = ingredientDao.getAll();
        Collection<Integer> ingredientsIds = recipes.stream()
                .map(recipe -> recipe.getIngredient().getId())
                .collect(Collectors.toList());
        int recipeTotalPrice = recipes.stream()
                .map(recipe -> recipe.getIngredient().getPriceForComposition() * recipe.getQuantity())
                .reduce(0, (a, b) -> a + b);
        Pizza pizza = new Pizza(
                null,
                genericName,
                10 + recipeTotalPrice
        );

        pizza = pizzaDao.save(pizza);
        ArrayList<Recipe> recipesWithId = new ArrayList<Recipe>();
        for (Recipe recipe: recipes) {
            recipesWithId.add(recipeDao.save(recipe, pizza));
        }
        pizza.setRecipes(recipesWithId);
        return pizza;
    }

    static int countTotalIngredient(Collection<Recipe> recipes) {
        AtomicInteger somme = new AtomicInteger(0);
        recipes.stream().forEach(recipe -> {
            somme.addAndGet(recipe.getQuantity());
        });
        return somme.get();
    }

    public Collection<Pizza> getAllStandard() {
        return pizzaDao.getAllStandard();
    }

    public Collection<Pizza> getAllWithCategory(String category) throws PizzaException {
        if (!categoryService.isExistingCategory(category)) {
            throw new PizzaException(ERROR_NO_SUCH_CATEGORY);
        }
        return pizzaDao.getAllWithCategory(category);
    }

    public Pizza savePizzaInDatabase(Pizza customPizza) {
        return pizzaDao.save(customPizza);
    }
}
