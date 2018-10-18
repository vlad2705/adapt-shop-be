package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Category;
import cz.cvut.fel.repository.CategoryRepository;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.web.filter.CategoryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CategoryRepositoryImpl extends AbstractDAO<Category, Long> implements CategoryRepository, ICanDelete<Category> {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public CategoryRepositoryImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findRoots() {
        Query query = getEntityManager().createQuery("SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.name ASC");
        
        return query.getResultList();
    }
    
    @Override
    public Category findByName(String name) {
        Query query = getEntityManager().createQuery("SELECT c FROM Category c WHERE c.name = :name ");
        query.setParameter("name", name);
        try {
            return (Category)query.getSingleResult();
        } catch (NoResultException | EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findChildren(Category category) {
        Query query = getEntityManager().createQuery("SELECT c FROM Category c WHERE c.parent = :parent ORDER BY c.name ASC");
        query.setParameter("parent", category);
        
        return query.getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Long> findChildren(long categoryId) {
        Query query = getEntityManager().createQuery("SELECT c.id FROM Category c WHERE c.parent.id = :parentId");
        query.setParameter("parentId", categoryId);
    
        return query.getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findAll(long id) {
        Query query = getEntityManager().createQuery("SELECT c FROM Category c WHERE c.id <> :id ORDER BY c.name ASC");
        query.setParameter("id", id);
        
        return query.getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findByFilter(CategoryFilter filter) {
        Query query = getEntityManager().createQuery(filter.getQueryString());
        filter.setParameters(query);
        return query.getResultList();
    }
    
    public void save(Category category) {
        checkCanCreate(category.getName()).throwEx();
        this.getEntityManager().persist(category);
    }
    
    @Override
    public Category update(Category category) {
        checkCanUpdate(category).throwEx();
        return getEntityManager().merge(category);
        
    }
    
    private BusinessCheckResult checkCanCreate(String name) {
        return new BusinessCheckSimpleResult(findByName(name) == null, "%s already exists!", name);
    }
    
    private BusinessCheckResult checkCanUpdate(Category category) {
        Category category1 = findByName(category.getName());
        return new BusinessCheckSimpleResult(category1 == null || category1.equals(category), "%s already exists!", category.getName());
    }
    
    @Override
    public BusinessCheckResult checkCanDelete(Category category) {
        return new BusinessCheckSimpleResult(category != null && productRepository.findByCategory(category.getId()).isEmpty(), "This category does not exist!");
    }
}
