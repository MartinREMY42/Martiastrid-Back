package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.IngredientDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static eu.busi.martiastrid.constants.Constants.MAX_AMOUNT_INGREDIENTS_CUSTOM_PIZZA;
import static eu.busi.martiastrid.constants.Constantsi18n.*;

@Service
public class PizzaService {

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private CategoryService categoryService;

    public Pizza createCustomPizza(Collection<Integer> ingredientsIds) throws PizzaException {

        if(Objects.isNull(ingredientsIds) || ingredientsIds.size() < 1 ){
            throw new PizzaException(ERROR_NOT_ENOUGH_INGREDIENTS_SELECTED);
        }

        if (ingredientsIds.size() > MAX_AMOUNT_INGREDIENTS_CUSTOM_PIZZA) {
            throw new PizzaException(ERROR_TOO_MUCH_INGREDIENTS_SELECTED);
        }

        Collection<Ingredient> allIngredients = ingredientDao.getAll();
        Collection<Ingredient> ingredientCollecttion = allIngredients.stream().filter(i -> ingredientsIds.contains(i.getId())).collect(Collectors.toList());
        Pizza pizza = new Pizza(
                null,
                "CustomPizza",
                10 + ingredientCollecttion.stream().map(Ingredient::getPriceForComposition).reduce(0, (a, b) -> a + b),
                ingredientCollecttion,
                true,
                null
        );

        return pizza;

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
