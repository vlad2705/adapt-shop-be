package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Product;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.web.client.dto.ClientProductDto;
import cz.cvut.fel.web.client.filter.ClientProductFilter;
import cz.cvut.fel.web.data.ProductData;
import cz.cvut.fel.web.filter.ProductFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl extends AbstractDAO<Product, Long> implements ProductRepository, ICanDelete<Product> {
    
    @Override
    public long findRowCount(ProductFilter filter) {
        Query query = getEntityManager().createQuery(filter.getRowCountQuery());
        filter.setParameters(query);
        return (long) query.getSingleResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<ProductData> findByFilter(ProductFilter filter) {
        Query query = getEntityManager().createQuery(filter.getQueryString());
        filter.setParameters(query);
        query.setFirstResult(filter.getFirst());
        query.setMaxResults(filter.getPageSize());
        return (List<ProductData>) query.getResultList().stream().map(data -> new ProductData((Object[]) data)).collect(Collectors.toList());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<ClientProductDto> findByClientFilter(ClientProductFilter filter) {
        Query query = getEntityManager().createQuery(filter.getQueryString());
        filter.setParameters(query);
        query.setFirstResult(filter.getFirst());
        query.setMaxResults(filter.getPageSize());
        return (List<ClientProductDto>) query.getResultList().stream().map(data -> new ClientProductDto((Object[]) data)).collect(Collectors.toList());
    }
    
    @Override
    public long findRowCountByClientFilter(ClientProductFilter filter) {
        Query query = getEntityManager().createQuery(filter.getRowCountQuery());
        filter.setParameters(query);
        return (long) query.getSingleResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> findByCategory(long categoryId) {
        Query query = getEntityManager().createQuery("SELECT p FROM Product p WHERE p.category.id = :categoryId");
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Product> findByName(String name) {
        Query query = getEntityManager().createQuery("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%',:productName,'%')");
        query.setParameter("productName", name);
        return query.getResultList();
    }
    
    @Override
    public Long findPictureIdByProduct(long productId) {
        Query query = getEntityManager().createQuery("SELECT p.picture.id FROM Product p WHERE p.id = :productId");
        query.setParameter("productId", productId);
        return (Long)query.getSingleResult();
    }
    
    @Override
    public BusinessCheckResult checkCanDelete(Product product) {
        return new BusinessCheckSimpleResult(product != null, "This product does not exist!");
    }
}
