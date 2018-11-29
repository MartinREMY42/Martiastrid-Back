package eu.busi.martiastrid.service;

import eu.busi.martiastrid.constants.Constantsi18n;
import eu.busi.martiastrid.dataAccess.dao.OrderDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.form.PizzaCounter;
import eu.busi.martiastrid.service.authentication.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

@Service
public class CartService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PizzaDao pizzaDao;

    public void incrementPizzaInCart(Pizza pizza, HashMap<Pizza, Integer> cart) {
        Integer quantity = cart.get(pizza);
        if (!Objects.isNull(quantity)) {
            cart.put(pizza, quantity + 1);
        }
    }

    public HashMap<Pizza, Integer> loadCartFromDatabase() throws PizzaException {
        HashMap<Pizza, Integer> cart = new HashMap<>();
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        try {
            Order order = orderDao.getOpenOrderForUser(username);
            if (!Objects.isNull(order.getOrderLines())) {
                order.getOrderLines().stream().filter(orderLine -> orderLine.getQuantity() > 0).forEach(l -> cart.put(l.getPizza(), l.getQuantity()));
            }
        } catch (PizzaDatabaseException e) {
            throw new PizzaException(Constantsi18n.ERROR_RETRIEVING_ORDER_FROM_DB);
        }
        return cart;
    }

    public void decrementPizzaInCart(Pizza pizza, HashMap<Pizza, Integer> cart) {
        int quantity = cart.get(pizza) - 1;
        if (quantity <= 0) {
            cart.remove(pizza);
        } else {
            cart.put(pizza, quantity);
        }
    }

    public void addCustomPizzasToCart(Pizza customPizza, HashMap<Pizza, Integer> cart, Integer quantity) {
        if (cart.containsKey(customPizza) && quantity > 0) {
            quantity += cart.get(customPizza);
        }
        cart.put(customPizza, quantity);
    }

    public void putAllPizzasToCart(PizzaCounter pizzaCounter, HashMap<Pizza, Integer> cart) {
        Collection<Pizza> allStandardPizzas = pizzaDao.getAllStandard();

        pizzaCounter
                .getOrderedPizzas()
                .entrySet().stream()
                .forEach((entry) -> {
                    Integer pizzaId = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (quantity != null && quantity > 0) {
                        Pizza pizza = allStandardPizzas.stream()
                                .filter(p -> p.getId().equals(pizzaId))
                                .findFirst().get();
                        // orderedPizzas.put(pizza, quantity);
                        cart.put(pizza, quantity);
                    }
                });

    }
}
