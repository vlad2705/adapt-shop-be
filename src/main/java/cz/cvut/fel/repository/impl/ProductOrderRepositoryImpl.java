package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.ProductOrder;
import cz.cvut.fel.repository.ProductOrderRepository;
import cz.cvut.fel.web.data.ProductOrderData;
import cz.cvut.fel.web.filter.ProductOrderFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductOrderRepositoryImpl extends AbstractDAO<ProductOrder, Long> implements ProductOrderRepository, ICanDelete<ProductOrder> {
    
    @Override
    public long findRowCount(ProductOrderFilter filter) {
        Query query = getEntityManager().createQuery(filter.getRowCountQuery());
        filter.setParameters(query);
        return (long) query.getSingleResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<ProductOrderData> findByFilter(ProductOrderFilter filter) {
        Query query = getEntityManager().createQuery(filter.getQueryString());
        filter.setParameters(query);
        query.setFirstResult(filter.getFirst());
        query.setMaxResults(filter.getPageSize());
        return (List<ProductOrderData>) query.getResultList().stream().map(data -> new ProductOrderData((Object[]) data)).collect(Collectors.toList());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<ProductOrder> findByPersonAndOrderStatus(long personId, long orderStatusId) {
        Query query = getEntityManager().createQuery("SELECT po FROM ProductOrder po WHERE po.person.id = :personId AND po.orderStatus.id = :orderStatusId ");
        query.setParameter("personId", personId);
        query.setParameter("orderStatusId", orderStatusId);
        return query.getResultList();
    }
    
    @Override
    public BusinessCheckResult checkCanDelete(ProductOrder productOrder) {
        return new BusinessCheckSimpleResult(productOrder != null, "This product does not exist!");
    }
}
