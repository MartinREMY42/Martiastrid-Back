package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.PizzaQuantity;
import eu.busi.martiastrid.service.PizzaService;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RequestMapping("/api/pizzas")
@RestController
@CrossOrigin("*")
public class PremadePizzaRestController {

    @Autowired
    private PizzaService pizzaService;



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
    public List<PizzaQuantity> addToCart(@RequestBody List<PizzaQuantity> orderedPizza) {
        System.out.println("first ---------------------------" + orderedPizza);
        orderedPizza.add(
                new PizzaQuantity(
                        new Pizza(1, "genericPizzaName", 10, new ArrayList<>(), false, new TreeSet<>()),
                        5));
        System.out.println("then ---------------------------" + orderedPizza);
        return orderedPizza;
    }
}
