package eu.busi.martiastrid.service;

import eu.busi.martiastrid.constants.Constantsi18n;
import eu.busi.martiastrid.dataAccess.dao.IngredientDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

@Service
public class IngredientService {

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private PizzaDao pizzaDao;

    public Collection<Ingredient> getAllFromDatabase() {
        return ingredientDao.getAll();

    }

    public Ingredient getIngredientById(int idIngredient) {
        return ingredientDao.findById(idIngredient);
    }

    public boolean checkEnoughIngredients(Collection<Integer> ingredientsIds, Integer quantity) {
        Collection<Ingredient> allIngredients = ingredientDao.getAll();
        return allIngredients.stream() // checks quantities
                .filter(i -> ingredientsIds.contains(i.getId()))
                .allMatch(ingredient1 -> ingredient1.getStockQuantity() - quantity > 0);
    }

    public boolean checkIfEnoughIngredientsForPizzas(HashMap<Integer, Integer> orderedPizzas) throws PizzaException {
        HashMap<Pizza, Integer> pizzas = new HashMap<>();
        orderedPizzas.forEach(
                (pizzaId, quantity) -> {
                    if (!Objects.isNull(quantity) && quantity > 0) {
                        pizzas.put(pizzaDao.getById(pizzaId), quantity);
                        // TODO : ICI JE POURRAIS VERIFIER QUE C'EST PAS UN NOUVELLE CUSTOM
                    }
                }
        );

        for (Pizza pizza : pizzas.keySet()) {
            if (! pizza.getRecipes().stream()
                    .allMatch(recipe -> {
                        int quantite = pizzas.get(pizza) * recipe.getQuantity();
                        return recipe.getIngredient().getStockQuantity() - quantite >= 0;
                    })) {
                throw new PizzaException(Constantsi18n.ERROR_NOT_ENOUGH_INGREDIENTS_IN_STOCK);
            }
        }

        return true;
    }

}
