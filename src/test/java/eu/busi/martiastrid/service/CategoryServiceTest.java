package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.CategoryDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    CategoryDao categoryDao;

    @InjectMocks
    CategoryService categoryService;

    @Before
    public void setUp(){
        List<String> categories = new ArrayList<>();
        categories.add("category_a");
        categories.add("category_b");
        categories.add("category_c");
        when(categoryDao.getAll()).thenReturn(categories);
    }

    @Test()
    public void isExistingCategory() {
        assertTrue(categoryService.isExistingCategory("category_a"));
    }

    @Test()
    public void isNonExistingCategory() {
        assertFalse(categoryService.isExistingCategory("category_d"));
    }
}