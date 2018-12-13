package eu.busi.martiastrid.dataAccess.dao;

import eu.busi.martiastrid.dataAccess.entity.CategoryEntity;
import eu.busi.martiastrid.dataAccess.entity.PizzaEntity;
import eu.busi.martiastrid.dataAccess.entity.RecipeEntity;
import eu.busi.martiastrid.dataAccess.repository.PizzaRepository;
import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PizzaDao {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private ProviderConverter providerConverter;
    
    public Pizza getById(Integer id) {
        return providerConverter.pizzaEntityToModel(pizzaRepository.getOne(id));
    }

    public Collection<Pizza> getAllStandard(){
        Collection<PizzaEntity> pizzaEntities = pizzaRepository.findAllByGenericNameIsNotContaining("Pizza");
        Collection<Pizza> pizzas = pizzaEntities.stream()
                .map(providerConverter::pizzaEntityToModel)
                .collect(Collectors.toList());
        return pizzas;
    }

    public Pizza save(Pizza pizza){
        PizzaEntity pizzaEntity = providerConverter.pizzaModelToEntity(pizza);
        pizzaEntity = pizzaRepository.save(pizzaEntity);
        return providerConverter.pizzaEntityToModel(pizzaEntity);
    }

    public Collection<Pizza> getAllWithCategory(String category) {
        Set<CategoryEntity> categoryEntities = new HashSet<>();
        categoryEntities.add(new CategoryEntity(category));
        Collection<PizzaEntity> pizzaEntities = pizzaRepository.findAllByCategoryEntityContains(categoryEntities);
        Collection<Pizza> pizzas = pizzaEntities.stream()
                .map(providerConverter::pizzaEntityToModel)
                .collect(Collectors.toList());
        return pizzas;
    }
}
