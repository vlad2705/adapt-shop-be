package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends IDAO<OrderItem, Long> {
    
    List<OrderItem> findByProductOrder(long productOrderId);
    
    List<OrderItem> findByProductOrderAndProduct(long productOrderId, long productId);
    
    Long findQuantityCountByProductOrder(long productOrderId);
    
    void save(OrderItem orderItem);
}
