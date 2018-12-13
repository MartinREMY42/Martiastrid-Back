package eu.busi.martiastrid.dataAccess.dao;

import eu.busi.martiastrid.dataAccess.entity.IngredientEntity;
import eu.busi.martiastrid.dataAccess.repository.IngredientRepository;
import eu.busi.martiastrid.dataAccess.util.ProviderConverter;
import eu.busi.martiastrid.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientDao {

    @Autowired
    private IngredientRepository ingredientRepository;
    
    @Autowired
    private ProviderConverter providerConverter;

    public Collection<Ingredient> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(providerConverter::ingredientEntityToModel)
                .collect(Collectors.toList());
    }

    public Collection<Ingredient> getExceptPatte(){
        return ingredientRepository.findAllByGenericNameNotContaining("patte")
                    .stream()
                    .map(providerConverter::ingredientEntityToModel)
                    .collect(Collectors.toList());
    }

    public void saveAll(Collection<Ingredient> ingredients) {
        ingredients.forEach(
                ingredient -> {
                    IngredientEntity ingredientEntity = providerConverter.ingredientModelToEntity(ingredient);
                    ingredientRepository.save(ingredientEntity);
                }
        );
    }

    public Ingredient findById(Integer id) {
        Optional<IngredientEntity> ingredientEntity = this.ingredientRepository.findById(id);
        if (ingredientEntity.isPresent()) {
            return this.providerConverter.ingredientEntityToModel(ingredientEntity.get());
        }
        return null;
    }

}
