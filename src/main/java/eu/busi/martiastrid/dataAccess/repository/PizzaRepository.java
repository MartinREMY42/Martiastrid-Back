package eu.busi.martiastrid.dataAccess.repository;

import eu.busi.martiastrid.dataAccess.entity.CategoryEntity;
import eu.busi.martiastrid.dataAccess.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface PizzaRepository extends JpaRepository<PizzaEntity, Integer> {
    Collection<PizzaEntity> findAllByCustomFalse();

    Collection<PizzaEntity> findAllByCategoryEntityContains(Set<CategoryEntity> categoryEntity);
}
