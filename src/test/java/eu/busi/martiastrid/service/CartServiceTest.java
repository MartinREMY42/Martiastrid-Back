package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.OrderDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.OrderLine;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.service.authentication.IAuthenticationFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @Mock
    private IAuthenticationFacade authenticationFacade;

    @Mock
    private OrderDao orderDao;

    @Mock
    private PizzaDao pizzaDao;

    @InjectMocks
    CartService cartService;

    private static List<Ingredient> ingredientListPizza1;
    private static List<Ingredient> ingredientListPizza2;

    @Before
    public void setUp() throws Exception {
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
    }

    @Test
    public void incrementPizzaInCart_customPizzaPresentInValidCart(){
        Pizza pizza = new Pizza(
                null,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        );

        HashMap<Pizza, Integer> cart = new HashMap<>();
        cart.put(new Pizza(
                null,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        ), 2);
        assertEquals(cart.get(pizza), new Integer(2));
        assertEquals(cart.keySet().size(), 1);
        cartService.incrementPizzaInCart(pizza, cart);
        assertEquals(cart.get(pizza), new Integer(3));
        assertEquals(cart.keySet().size(), 1);

    }

    @Test
    public void incrementPizzaInCart_customPizzaNotPresentInValidCart(){
        Pizza pizza = new Pizza(
                2,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        );

        HashMap<Pizza, Integer> cart = new HashMap<>();
        cart.put(new Pizza(
                1,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        ), 2);
        assertNull(cart.get(pizza));
        assertEquals(cart.keySet().size(), 1);
        cartService.incrementPizzaInCart(pizza, cart);
        assertNull(cart.get(pizza));
        assertEquals(cart.keySet().size(), 1);

    }

    @Test
    public void incrementPizzaInCart_premadePizzaPresentInValidCart(){
        Pizza pizza = new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        );

        HashMap<Pizza, Integer> cart = new HashMap<>();
        cart.put(new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        ), 2);
        assertEquals(cart.get(pizza), new Integer(2));
        assertEquals(cart.keySet().size(), 1);
        cartService.incrementPizzaInCart(pizza, cart);
        assertEquals(cart.get(pizza), new Integer(3));
        assertEquals(cart.keySet().size(), 1);

    }

    @Test
    public void incrementPizzaInCart_premadePizzaNotPresentInValidCart(){
        Pizza pizza = new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        );

        HashMap<Pizza, Integer> cart = new HashMap<>();
        cart.put(new Pizza(
                2,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        ), 2);
        assertNull(cart.get(pizza));
        assertEquals(cart.keySet().size(), 1);
        cartService.incrementPizzaInCart(pizza, cart);
        assertNull(cart.get(pizza));
        assertEquals(cart.keySet().size(), 1);

    }

    @Test
    public void loadCartFromDatabase_normalCart(){
        when(authenticationFacade.getAuthentication()).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return "user";
            }
        });

        List<OrderLine> orderLines = new ArrayList<>();
        Pizza pizza1 = new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        );
        orderLines.add(new OrderLine(
                1,
                2,
                pizza1

        ));
        Pizza pizza2 = new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                false,
                null
        );
        orderLines.add(new OrderLine(
                2,
                3,
                pizza2

        ));
        try {
            when(orderDao.getOpenOrderForUser("user")).thenReturn(new Order(
                    1,
                    null,
                    null,
                    0,
                    orderLines,
                    null,
                    null
            ));

           HashMap<Pizza, Integer> cart = cartService.loadCartFromDatabase();
           assertEquals(cart.size(), 2);
           assertNotNull(cart.get(pizza1));
           assertNotNull(cart.get(pizza2));
           assertEquals(cart.get(pizza1), new Integer(2));
           assertEquals(cart.get(pizza2), new Integer(3));
        } catch (PizzaDatabaseException|PizzaException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadCartFromDatabase_emptyCart(){
        when(authenticationFacade.getAuthentication()).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "user";
            }
        });

        try {
            when(orderDao.getOpenOrderForUser("user")).thenReturn(new Order(
                    1,
                    null,
                    null,
                    0,
                    null,
                    null,
                    null
            ));

            HashMap<Pizza, Integer> cart = cartService.loadCartFromDatabase();
            assertTrue(cart.keySet().isEmpty());
        } catch (PizzaDatabaseException e) {
            e.printStackTrace();
        } catch (PizzaException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void decrementPizzaInCart_validCartForPizzaCustom() {
        HashMap<Pizza, Integer> cart = new HashMap<>();
        Pizza pizza1 = new Pizza(
                null,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        );

        Pizza pizza2 = new Pizza(
                null,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        );
        cart.put(pizza1, 2);
        cart.put(pizza2, 2);
        cartService.decrementPizzaInCart(pizza1, cart);
        assertEquals(cart.entrySet().size(), 2);
        assertEquals(cart.get(pizza1), new Integer(1));
        assertEquals(cart.get(pizza2), new Integer(2));
    }

    @Test
    public void decrementPizzaInCart_validCartForPizzaPremade() {
        HashMap<Pizza, Integer> cart = new HashMap<>();
        Pizza pizza1 = new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        );

        Pizza pizza2 = new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        );
        cart.put(pizza1, 2);
        cart.put(pizza2, 2);
        cartService.decrementPizzaInCart(pizza1, cart);
        assertEquals(cart.entrySet().size(), 2);
        assertEquals(cart.get(pizza1), new Integer(1));
        assertEquals(cart.get(pizza2), new Integer(2));
    }

    @Test
    public void addCustomPizzasToCart_validCart() {
        HashMap<Pizza, Integer> cart = new HashMap<>();
        Pizza pizza1 = new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        );

        Pizza pizza2 = new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        );
        cart.put(pizza1, 2);
        cart.put(pizza2, 2);
        cartService.addCustomPizzasToCart(pizza1, cart, 4);
        assertEquals(cart.entrySet().size(), 2);
        assertEquals(cart.get(pizza1), new Integer(6));
        assertEquals(cart.get(pizza2), new Integer(2));
    }

    @Test
    public void addCustomPizzasToCart_noPizzaInCart() {
        HashMap<Pizza, Integer> cart = new HashMap<>();
        Pizza pizza1 = new Pizza(
                1,
                null,
                null,
                ingredientListPizza1,
                true,
                null
        );

        Pizza pizza2 = new Pizza(
                2,
                null,
                null,
                ingredientListPizza2,
                true,
                null
        );
        cart.put(pizza1, 2);
        cartService.addCustomPizzasToCart(pizza2, cart, 4);
        assertEquals(cart.entrySet().size(), 2);
        assertEquals(cart.get(pizza1), new Integer(2));
        assertEquals(cart.get(pizza2), new Integer(4));
    }



}