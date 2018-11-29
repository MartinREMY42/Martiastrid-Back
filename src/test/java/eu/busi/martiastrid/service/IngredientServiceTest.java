package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.IngredientDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Pizza;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceTest {

    @Mock
    private IngredientDao ingredientDao;

    @Mock
    private PizzaDao pizzaDao;

    @InjectMocks
    private IngredientService ingredientService;

    private static List<Ingredient> ingredientListPizza1;
    private static List<Ingredient> ingredientListPizza2;
    private static Collection<Ingredient> allIngredients;

    @BeforeClass
    public static void beforeClass(){
        ingredientListPizza1 = new ArrayList<>();
        ingredientListPizza1.add(new Ingredient(
                1,
                null,
                4,
                null
        ));
        ingredientListPizza1.add(new Ingredient(
                2,
                null,
                4,
                null
        ));
        ingredientListPizza1.add(new Ingredient(
                3,
                null,
                4,
                null
        ));

        ingredientListPizza2 = new ArrayList<>();
        ingredientListPizza2.add(new Ingredient(
                4,
                null,
                4,
                null
        ));
        ingredientListPizza2.add(new Ingredient(
                5,
                null,
                4,
                null
        ));
        ingredientListPizza2.add(new Ingredient(
                6,
                null,
                4,
                null
        ));

        allIngredients = new ArrayList<>();
        allIngredients.add(
                new Ingredient(
                        1,
                        null,
                        4,
                        null
                )
        );
        allIngredients.add(
                new Ingredient(
                        2,
                        null,
                        4,
                        null
                )
        );
    }


    @Test
    public void checkIfEnoughIngredientsForPizzas_enoughIngredientsCustom() throws PizzaException {
        when(pizzaDao.getById(1)).thenReturn(new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        ));

        when(pizzaDao.getById(2)).thenReturn(new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        ));

        HashMap<Integer, Integer> pizzaIdAndQuantity = new HashMap<>();
        pizzaIdAndQuantity.put(1, 3);
        pizzaIdAndQuantity.put(2, 3);
        assertTrue(ingredientService.checkIfEnoughIngredientsForPizzas(
                pizzaIdAndQuantity
        ));


    }

    @Test(expected = PizzaException.class)
    public void checkIfEnoughIngredientsForPizzas_notEnoughIngredientsCustom() throws PizzaException {
        when(pizzaDao.getById(1)).thenReturn(new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        ));

        when(pizzaDao.getById(2)).thenReturn(new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        ));

        HashMap<Integer, Integer> pizzaIdAndQuantity = new HashMap<>();
        pizzaIdAndQuantity.put(1, 5);
        pizzaIdAndQuantity.put(2, 5);

        ingredientService.checkIfEnoughIngredientsForPizzas(pizzaIdAndQuantity);


    }

    @Test
    public void checkIfEnoughIngredientsForPizzas_enoughIngredientsPremade() throws PizzaException {
        when(pizzaDao.getById(1)).thenReturn(new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        ));

        when(pizzaDao.getById(2)).thenReturn(new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                false,
                null
        ));

        HashMap<Integer, Integer> pizzaIdAndQuantity = new HashMap<>();
        pizzaIdAndQuantity.put(1, 3);
        pizzaIdAndQuantity.put(2, 3);
        assertTrue(ingredientService.checkIfEnoughIngredientsForPizzas(pizzaIdAndQuantity));


    }

    @Test(expected = PizzaException.class)
    public void checkIfEnoughIngredientsForPizzas_NotEnoughIngredientsPremade() throws PizzaException {
        when(pizzaDao.getById(1)).thenReturn(new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        ));

        when(pizzaDao.getById(2)).thenReturn(new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                false,
                null
        ));

        HashMap<Integer, Integer> pizzaIdAndQuantity = new HashMap<>();
        pizzaIdAndQuantity.put(1, 5);
        pizzaIdAndQuantity.put(2, 5);
        ingredientService.checkIfEnoughIngredientsForPizzas(pizzaIdAndQuantity);
    }

    @Test(expected = PizzaException.class)
    public void checkIfEnoughIngredientsForPizzas_premade_oneOk_otherNotOk() throws PizzaException {
        when(pizzaDao.getById(1)).thenReturn(new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        ));

        when(pizzaDao.getById(2)).thenReturn(new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                false,
                null
        ));

        HashMap<Integer, Integer> pizzaIdAndQuantity = new HashMap<>();
        pizzaIdAndQuantity.put(1, 3);
        pizzaIdAndQuantity.put(2, 5);
        ingredientService.checkIfEnoughIngredientsForPizzas(pizzaIdAndQuantity);
    }

    @Test(expected = PizzaException.class)
    public void checkIfEnoughIngredientsForPizzas_custom_oneOk_otherNotOk() throws PizzaException {
        when(pizzaDao.getById(1)).thenReturn(new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        ));

        when(pizzaDao.getById(2)).thenReturn(new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        ));

        HashMap<Integer, Integer> pizzaIdAndQuantity = new HashMap<>();
        pizzaIdAndQuantity.put(1, 3);
        pizzaIdAndQuantity.put(2, 5);
        ingredientService.checkIfEnoughIngredientsForPizzas(pizzaIdAndQuantity);
    }

    /*
        boolean checkEnoughIngredients(Collection<Integer> ingredientsIds, Integer quantity) {
        Collection<Ingredient> allIngredients = ingredientDao.getAll();
        return allIngredients.stream() // checks quantities
                .filter(i -> ingredientsIds.contains(i.getId()))
                .allMatch(ingredient1 -> ingredient1.getStockQuantity() - quantity > 0);
    }
     */

    @Test
    public void checkEnoughIngredients_ok(){
        when(ingredientDao.getAll()).thenReturn(allIngredients);
        List<Integer> ingredients = new ArrayList<>();
        ingredients.add(1);
        ingredients.add(2);
        assertTrue(ingredientService.checkEnoughIngredients(ingredients, 3));

    }

    @Test
    public void checkEnoughIngredients_ko(){
        when(ingredientDao.getAll()).thenReturn(allIngredients);
        List<Integer> ingredients = new ArrayList<>();
        ingredients.add(1);
        ingredients.add(2);
        assertFalse(ingredientService.checkEnoughIngredients(ingredients, 5));

    }




}