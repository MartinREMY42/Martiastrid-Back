package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.IngredientDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Pizza;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PizzaServiceTest {

    static Collection<Ingredient> allIngredients;
    @Mock
    IngredientDao ingredientDao;
    @InjectMocks
    PizzaService pizzaService;

    @BeforeClass
    public static void beforeClass() {
        allIngredients = new ArrayList<>();
        allIngredients.add(new Ingredient(1, null, null, 1));
        allIngredients.add(new Ingredient(2, null, null, 2));
        allIngredients.add(new Ingredient(3, null, null, 3));
        allIngredients.add(new Ingredient(4, null, null, 4));
        allIngredients.add(new Ingredient(5, null, null, 5));
        allIngredients.add(new Ingredient(6, null, null, 6));
        allIngredients.add(new Ingredient(7, null, null, 7));
        allIngredients.add(new Ingredient(8, null, null, 8));
    }


    @Test
    public void createCustomPizza_okCase() throws PizzaException {
        when(ingredientDao.getAll()).thenReturn(allIngredients);
        List<Integer> ingredientsIds = new ArrayList<>();
        ingredientsIds.add(1);
        ingredientsIds.add(2);
        ingredientsIds.add(3);

        Pizza pizza = pizzaService.createCustomPizza(ingredientsIds);
        assertEquals(pizza.getIngredients().size(), 3);
    }

    @Test(expected = PizzaException.class)
    public void createCustomPizza_tooMuchIngredients() throws PizzaException {
        List<Integer> ingredientsIds = new ArrayList<>();
        ingredientsIds.add(1);
        ingredientsIds.add(2);
        ingredientsIds.add(3);
        ingredientsIds.add(4);
        ingredientsIds.add(5);
        ingredientsIds.add(6);
        ingredientsIds.add(7);

        pizzaService.createCustomPizza(ingredientsIds);
    }

    @Test(expected = PizzaException.class)
    public void createCustomPizza_notEnoughIngredients() throws PizzaException {
        List<Integer> ingredientsIds = new ArrayList<>();
        pizzaService.createCustomPizza(ingredientsIds);
    }
}