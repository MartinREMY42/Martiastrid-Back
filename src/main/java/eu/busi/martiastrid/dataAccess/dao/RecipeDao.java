package eu.busi.martiastrid.dataAccess.dao;

import eu.busi.martiastrid.dataAccess.entity.PizzaEntity;
import eu.busi.martiastrid.dataAccess.entity.RecipeEntity;
import eu.busi.martiastrid.dataAccess.repository.RecipeRepository;
import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecipeDao {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProviderConverter providerConverter;

    public Recipe save(Recipe recipe, Pizza pizza){

        PizzaEntity pizzaEntity = providerConverter.pizzaModelToEntity(pizza);
        RecipeEntity recipeEntity = providerConverter.recipeModelToEntity(recipe, pizzaEntity);
        recipeRepository.save(recipeEntity);
        recipe =  providerConverter.recipeEntityToModel(recipeEntity);
        return recipe;
    }
}
