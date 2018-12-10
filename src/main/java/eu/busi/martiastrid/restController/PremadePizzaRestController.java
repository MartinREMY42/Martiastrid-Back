package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.PizzaQuantity;
import eu.busi.martiastrid.service.OrderService;
import eu.busi.martiastrid.service.PizzaService;
import eu.busi.martiastrid.service.authentication.CartRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static eu.busi.martiastrid.constants.Constants.CART;

@RequestMapping("/api/pizzas")
@RestController
@CrossOrigin("*")
public class PremadePizzaRestController {

    @Autowired
    private ProviderConverter providerConverter;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartRestService cartRestService;

    @Autowired
    private OrderService orderService;

    /**
     * @return liste de toutes mes pizzas
     */
    @GetMapping("")
    public List<Pizza> getAll(){

        List<Pizza> pizzas = pizzaService.getAllStandard().stream()
                .collect(Collectors.toList());
        return pizzas;
    }

    /**
     * je veux retourner toutes mes pizzas d'une categorie
     * URL: http://localhost:8082/martiastrid/pizzas/vegan
     * @param category
     * @return List<Pizza>
     */
    @GetMapping("/{category}")
    public List<Pizza> getPizzaByCategory(@PathVariable("category") String category){
        RequestAttribute requestAttribute;
        List<Pizza> pizzas = null;
        try {
            pizzas = pizzaService.getAllWithCategory(category).stream()
                    .collect(Collectors.toList());
        } catch (PizzaException e) {
            System.out.println(e.getMessage());
        }
        return pizzas;
    }

    /**
     * @return le panier sous une forme de pseudo-map
     */
    @PostMapping("/addToCart")
    public ResponseEntity<List<PizzaQuantity>> addToCart(@RequestBody List<PizzaQuantity> orderedPizza) {
        System.out.println("\n\n\nPremadePizzaRestController.addToCart");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PizzaQuantity> cart = this.cartRestService.mergeToCurrentCart(orderedPizza, username);
        try {
            Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
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
