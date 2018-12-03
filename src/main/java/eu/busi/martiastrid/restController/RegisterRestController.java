package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.Authority;
import eu.busi.martiastrid.model.User;
import eu.busi.martiastrid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registerUser")
@CrossOrigin("*")
public class RegisterRestController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){

        /**
         * {
         * 	"username": "astrid",
         * 	"password": "1234",
         * 	"enabled": true,
         * 	"nonExpired": true,
         * 	"nonLocked": true,
         * 	"credentialsNonExpired": true,
         * 	"authorities":[
         *                {
         * 			"authority": "ROLE_USER"
         *        }
         * 	],
         * 	"birthDate": "1990-10-06",
         * 	"creationDate": "2018-12-03",
         * 	"orders": []
         *
         * } pour
         */
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PizzaDatabaseException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
