package eu.busi.martiastrid.restController;


import eu.busi.martiastrid.constants.Constants;
import eu.busi.martiastrid.dataAccess.dao.PizzaDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.service.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/customPizza")
@CrossOrigin("*")
public class CustomPizzaRestController {

    private PizzaService pizzaService;

    /**
     * Je reçois et les données de mon formulaire et j'insert
     * dans ma BD
     * @param ingredientsIds
     * @return
     */
    @PostMapping
    public ResponseEntity<Pizza> createPizzaCustom(@RequestBody Collection<Integer> ingredientsIds){
        Pizza result = null;
        try {
             result = pizzaService.createCustomPizza(ingredientsIds);
        } catch (PizzaException e) {
            System.out.println(e.getMessage());
        }
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK)
                                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
