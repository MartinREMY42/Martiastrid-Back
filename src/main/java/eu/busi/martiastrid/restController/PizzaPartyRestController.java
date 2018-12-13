package eu.busi.martiastrid.restController;


import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.PizzaQuantity;
import eu.busi.martiastrid.model.RecipesQuantity;
import eu.busi.martiastrid.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pizzaParty")
@CrossOrigin("*")
public class PizzaPartyRestController {

    @Autowired
    private PizzaService pizzaService;

    /**
     * J
     * @param pizzaPartys décrit la pizza que l'utilisateur voudrait créer
     * @return
     */
    @PostMapping("/make")
    public ResponseEntity<PizzaQuantity> attemptPizzaPartyCreation(@RequestBody RecipesQuantity pizzaPartys){
        Pizza result = null;
        try {
            result = pizzaService.createNonStandardPizza("PizzaParty", pizzaPartys.getRecipes());
        } catch (PizzaException e) {
            System.out.println(e.getMessage());
        }
        return result != null ?
                new ResponseEntity<>(new PizzaQuantity(result, pizzaPartys.getQuantity()),
                        HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}