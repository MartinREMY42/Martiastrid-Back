package eu.busi.martiastrid.dataAccess.repository;

import eu.busi.martiastrid.dataAccess.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {
}