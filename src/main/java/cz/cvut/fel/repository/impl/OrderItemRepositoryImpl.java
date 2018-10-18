package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.OrderItem;
import cz.cvut.fel.repository.OrderItemRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderItemRepositoryImpl extends AbstractDAO<OrderItem, Long> implements OrderItemRepository, ICanDelete<OrderItem> {
    @Override
    public BusinessCheckResult checkCanDelete(OrderItem orderItem) {
        return new BusinessCheckSimpleResult(orderItem != null, "This product does not exist!");
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<OrderItem> findByProductOrder(long productOrderId) {
        Query query = getEntityManager().createQuery("SELECT oi FROM OrderItem oi WHERE oi.productOrder.id = :productOrderId ORDER BY oi.id");
        query.setParameter("productOrderId", productOrderId);
        return query.getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<OrderItem> findByProductOrderAndProduct(long productOrderId, long productId) {
        Query query = getEntityManager().createQuery("SELECT oi FROM OrderItem oi WHERE oi.productOrder.id = :productOrderId AND oi.product.id = :productId");
        query.setParameter("productOrderId", productOrderId);
        query.setParameter("productId", productId);
        return query.getResultList();
    }
    
    @Override
    public Long findQuantityCountByProductOrder(long productOrderId) {
        Query query = getEntityManager().createQuery("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.productOrder.id = :productOrderId");
        query.setParameter("productOrderId", productOrderId);
        return (Long)query.getSingleResult();
    }
}
