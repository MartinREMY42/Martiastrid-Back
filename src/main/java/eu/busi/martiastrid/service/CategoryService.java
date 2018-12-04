package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public boolean isExistingCategory(String category) {
        Collection<String> categories = categoryDao.getAll();
        return categories.contains(category);
    }

    public Collection<String> getAll(){
        return categoryDao.getAll();
    }
}
