package eu.busi.martiastrid.dataAccess.repository;

import eu.busi.martiastrid.dataAccess.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Integer> {

    Collection<IngredientEntity> findAllByGenericNameNotContaining(String patte);
}
