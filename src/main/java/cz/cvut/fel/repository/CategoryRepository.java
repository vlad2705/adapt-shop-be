package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Category;
import cz.cvut.fel.web.filter.CategoryFilter;

import java.util.List;

public interface CategoryRepository extends IDAO<Category, Long> {
    List<Category> findRoots();
    
    Category findByName(String name);
    
    List<Category> findChildren(Category category);
    
    List<Long> findChildren(long categoryId);
    
    List<Category> findAll(long id);
    
    List<Category> findByFilter(CategoryFilter filter);
    
    void save(Category category);
}
