package eu.busi.martiastrid.restController;

import eu.busi.martiastrid.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/categories")
@RestController
@CrossOrigin("*")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<String> getAll(){
        return categoryService.getAll().stream().collect(Collectors.toList());
    }
}
