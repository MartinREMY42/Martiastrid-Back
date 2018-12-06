
package eu.busi.martiastrid.restController;


import eu.busi.martiastrid.configuration.TokenProvider;
import eu.busi.martiastrid.model.LoginUser;
import eu.busi.martiastrid.model.User;
import eu.busi.martiastrid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static eu.busi.martiastrid.constants.Constants.HEADER_STRING;
import static eu.busi.martiastrid.constants.Constants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/whoIAm")
@CrossOrigin("*")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(Principal principal){

        User user = userService.getUser(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
