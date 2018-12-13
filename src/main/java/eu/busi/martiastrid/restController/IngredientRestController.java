package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.model.Ingredient;
import eu.busi.martiastrid.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/ingredients")
@RestController
@CrossOrigin("*")
public class IngredientRestController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<Ingredient> getAll(){
        return ingredientService.getAllFromDatabase().stream().collect(Collectors.toList());
    }

    @GetMapping("/exceptPatte")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<Ingredient> getAllExceptPatte(){
        return ingredientService.getAllFromDatabaseExceptPatte().stream().collect(Collectors.toList());
    }

}
