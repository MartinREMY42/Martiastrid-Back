package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.model.PizzaQuantity;
import eu.busi.martiastrid.service.authentication.CartRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(headers = "Accept=application/json", value = "/api/cart")
@CrossOrigin("*")
public class CartRestController {

    @Autowired
    private CartRestService cartRestService;

    @GetMapping("/getUserCart")
    ResponseEntity<List<PizzaQuantity>> getUserCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(this.cartRestService.getUserCart(username),
                HttpStatus.OK);
    }

}
