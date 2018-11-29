package eu.busi.martiastrid.dataAccess.dao;

import eu.busi.martiastrid.dataAccess.entity.CategoryEntity;
import eu.busi.martiastrid.dataAccess.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryDao {
    @Autowired
    private CategoryRepository categoryRepository;

    public Collection<String> getAll(){
        return categoryRepository.findAll().stream().map(CategoryEntity::getCategory).collect(Collectors.toSet());
    }
}
