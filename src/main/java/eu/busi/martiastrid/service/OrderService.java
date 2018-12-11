package eu.busi.martiastrid.service;

import eu.busi.martiastrid.constants.Constantsi18n;
import eu.busi.martiastrid.dataAccess.dao.IngredientDao;
import eu.busi.martiastrid.dataAccess.dao.OrderDao;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.dataAccess.dao.UserDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.*;
import eu.busi.martiastrid.service.authentication.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private IngredientService ingredientService;

    public void closePayedOrder(String refPaypal, int sum) throws PizzaException {
        try {
            Order order = orderDao.getOpenOrderForUser(authenticationFacade.getAuthentication().getName());
            List<Ingredient> allIngredients = new ArrayList<>(ingredientDao.getAll());
            order.getOrderLines().stream()
                    .filter(orderLine -> orderLine.getQuantity() > 0)
                    .forEach(
                            orderLine -> {
                                orderLine.getPizza().getIngredients().forEach(
                                        ingredient -> {
                                            Ingredient ingredientInAllIngredients = allIngredients.get(allIngredients.indexOf(ingredient));
                                            ingredientInAllIngredients.deCreaseStock(orderLine.getQuantity());
                                        }
                                );
                            }
                    );

            ingredientDao.saveAll(allIngredients);
            order.setPayment(
                    new Payment(
                            null,
                            Date.valueOf(LocalDate.now()),
                            refPaypal,
                            sum
                    )
            );
            orderDao.save(order);

        } catch (PizzaDatabaseException e) {
            throw new PizzaException(Constantsi18n.ERROR_RETRIEVING_ORDER_FROM_DB);
        }
    }

    // rest equivalent
    public Order getOrderForConnectedUserOrCreateIfNonExistent(String username) {
        Order order;
        try {
            order = orderDao.getOpenOrderForUser(username);
        } catch (PizzaDatabaseException e) {
            User user = userDao.getByUsername(username);

            order = new Order(
                    null,
                    Date.valueOf(LocalDate.now()),
                    null,
                    0,
                    null,
                    user,
                    null
            );

            order = orderDao.save(order);
        }
        return order;
    }

    public Order getOrderForConnectedUserOrCreateIfNonExistent() {
        Order order;
        try {
            order = orderDao.getOpenOrderForUser(authenticationFacade.getAuthentication().getName());
        } catch (PizzaDatabaseException e) {
            User user = userDao.getByUsername(authenticationFacade.getAuthentication().getName());

            order = new Order(
                    null,
                    Date.valueOf(LocalDate.now()),
                    null,
                    0,
                    null,
                    user,
                    null
            );

            order = orderDao.save(order);
        }
        return order;
    }

    // rest equivalent
    public void createOrderForConnectedUser(String username) {
        Order order = new Order(
                null,
                Date.valueOf(LocalDate.now()),
                null,
                0,
                null,
                userDao.getByUsername(authenticationFacade.getAuthentication().getName()),
                null
        );
        orderDao.save(order);
    }

    public void createOrderForConnectedUser() {
        Order order = new Order(
                null,
                Date.valueOf(LocalDate.now()),
                null,
                0,
                null,
                userDao.getByUsername(authenticationFacade.getAuthentication().getName()),
                null
        );
        orderDao.save(order);
    }

    public Integer calculateTotalAmount(Order order) {
        return order.getOrderLines().stream()
                .map(orderLine -> orderLine.getQuantity() * orderLine.getPizza().getPrice())
                .reduce(0, (a,b) -> a+b);
    }

    public void addAllPizzasToOrder(HashMap<Integer, Integer> orderedPizzas, Order order) throws PizzaException {
        if (orderedPizzas.values().stream().allMatch(integer -> Objects.isNull(integer) || integer <= 0)) {
            throw new PizzaException(Constantsi18n.ERROR_PIZZA_AMOUNT);
        }

        ingredientService.checkIfEnoughIngredientsForPizzas(orderedPizzas);

        HashMap<Pizza, Integer> pizzas = new HashMap<>();
        orderedPizzas.forEach(
                (pizzaId, quantity) -> {
                    if (!Objects.isNull(quantity) && quantity > 0) {
                        pizzas.put(pizzaDao.getById(pizzaId), quantity);
                    }
                }
        );

        pizzas.forEach(order::addPizza);
    }

    public void saveOrderInDatabase(Order order) {
        orderDao.save(order);
    }

}
