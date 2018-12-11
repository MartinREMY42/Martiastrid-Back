package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(headers = "Accept=application/json",value = "/api/pizzasFavorite")
@CrossOrigin("*")
public class PizzasFavoriteRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Pizza>> getPizzasFavorite(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return new ResponseEntity<>(userService.getPizzasFavorites(username),
                    HttpStatus.OK);
        } catch (PizzaDatabaseException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/{idPizza}")
    public ResponseEntity<List<Pizza>> switchPizzaFavoriteness(@PathVariable int idPizza){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<List<Pizza>>(userService.switchPizzaFavoriteness(username, idPizza),
                HttpStatus.OK);
    }
}
