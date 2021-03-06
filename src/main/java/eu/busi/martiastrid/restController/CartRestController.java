package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.PizzaQuantity;
import eu.busi.martiastrid.service.OrderService;
import eu.busi.martiastrid.service.PizzaService;
import eu.busi.martiastrid.service.restService.CartRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequestMapping(headers = "Accept=application/json", value = "/api/cart")
@RestController()
@CrossOrigin("*")
public class CartRestController {

    @Autowired
    private CartRestService cartRestService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private ProviderConverter providerConverter;

    @GetMapping("/getUserCart")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<List<PizzaQuantity>> getUserCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(this.cartRestService.getUserCart(username),
                HttpStatus.OK);
    }

    @PostMapping("/increment")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<List<PizzaQuantity>> incrementCart(@RequestBody Pizza pizza) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        this.cartRestService.increment(pizza, username);

        Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
        order.incrementPizza(pizza.getId());
        orderService.saveOrderInDatabase(order);
        return new ResponseEntity<>(this.cartRestService.getUserCart(username), HttpStatus.OK);
    }

    @PostMapping("/decrement")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<List<PizzaQuantity>> decrementCart(@RequestBody Pizza pizza) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        this.cartRestService.decrement(pizza, username);

        Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
        order.decrementPizza(pizza.getId());
        orderService.saveOrderInDatabase(order);
        return new ResponseEntity<>(this.cartRestService.getUserCart(username), HttpStatus.OK);
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<List<PizzaQuantity>> removeFromCart(@RequestBody Pizza pizza) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        this.cartRestService.remove(pizza, username);

        Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
        order.removePizza(pizza.getId());
        orderService.saveOrderInDatabase(order);
        return new ResponseEntity<>(this.cartRestService.getUserCart(username), HttpStatus.OK);
    }

    @PostMapping("/addAll")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<PizzaQuantity>> addToCart(@RequestBody List<PizzaQuantity> orderedPizza) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PizzaQuantity> cart = this.cartRestService.mergeToCurrentCart(orderedPizza, username);
        try {
            Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
            for (PizzaQuantity pizzaQuantity : orderedPizza) {
                if (Objects.isNull(pizzaQuantity.getPizza().getId())) {
                    pizzaService.savePizzaInDatabase(pizzaQuantity.getPizza());
                }
            }
            HashMap<Integer, Integer> pizzaCounter = providerConverter.pizzaQuantityToPizzaCounter(orderedPizza);
            orderService.addAllPizzasToOrder(pizzaCounter, order);
            orderService.saveOrderInDatabase(order);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (PizzaException p) {
            // TODO : permettre de transmettre ça en message au front
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
    }

}
