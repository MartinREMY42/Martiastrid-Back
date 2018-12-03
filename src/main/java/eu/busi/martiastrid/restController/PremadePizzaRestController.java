package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.constants.Constants;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.service.CartService;
import eu.busi.martiastrid.service.IngredientService;
import eu.busi.martiastrid.service.OrderService;
import eu.busi.martiastrid.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pizzas")
@CrossOrigin("*")
public class PremadePizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    /**
     * @return liste de toutes mes pizzas
     */

    @GetMapping("")
    public List getAll(){

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
}
