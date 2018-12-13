package eu.busi.martiastrid.dataAccess.dao;

import eu.busi.martiastrid.dataAccess.entity.PizzaEntity;
import eu.busi.martiastrid.dataAccess.entity.RecipeEntity;
import eu.busi.martiastrid.dataAccess.repository.RecipeRepository;
import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeDao {
    
    @Autowired
    RecipeRepository recipeRepository;
    
    @Autowired
    ProviderConverter providerConverter;
    
    public Collection<Recipe> getAll() {
        return recipeRepository.findAll()
                .stream()
                .map(providerConverter::recipeEntityToModel)
                .collect(Collectors.toList());
    }

    public void saveAllToPizza(Collection<Recipe> recipes, PizzaEntity pizzaEntity) {
        recipes.forEach(
                recipe -> {
                    RecipeEntity recipeEntity = providerConverter.recipeModelToEntity(recipe, pizzaEntity);
                    recipeRepository.save(recipeEntity);
                }
        );
    }

    public Recipe findById(Integer id) {
        Optional<RecipeEntity> recipeEntity = this.recipeRepository.findById(id);
        if (recipeEntity.isPresent()) {
            return this.providerConverter.recipeEntityToModel(recipeEntity.get());
        }
        return null;
    }
}
