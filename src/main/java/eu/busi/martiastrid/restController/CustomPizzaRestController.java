package eu.busi.martiastrid.restController;


import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.PizzaQuantity;
import eu.busi.martiastrid.model.RecipesQuantity;
import eu.busi.martiastrid.service.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customPizza")
@CrossOrigin("*")
public class CustomPizzaRestController {

    private PizzaService pizzaService;

    /**
     * J
     * @param customsPizzas décrit la pizza que l'utilisateur voudrait créer
     * @return
     */
    @PostMapping
    public ResponseEntity<PizzaQuantity> attemptCustomPizzaCreation(@RequestBody RecipesQuantity customPizzas){
        Pizza result = null;
        try {
             result = pizzaService.createNonStandardPizza("CustomPizza", customPizzas.getRecipes());
        } catch (PizzaException e) {
            System.out.println(e.getMessage());
        }
        return result != null ?
                new ResponseEntity<>(new PizzaQuantity(result, customPizzas.getQuantity()),
                        HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
