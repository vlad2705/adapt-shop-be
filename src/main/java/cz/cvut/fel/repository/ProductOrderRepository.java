package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.ProductOrder;
import cz.cvut.fel.web.data.ProductOrderData;
import cz.cvut.fel.web.filter.ProductOrderFilter;

import java.util.List;

public interface ProductOrderRepository extends IDAO<ProductOrder, Long> {
    long findRowCount(ProductOrderFilter filter);
    
    List<ProductOrderData> findByFilter(ProductOrderFilter filter);
    
    List<ProductOrder> findByPersonAndOrderStatus(long personId, long orderStatusId);
    
    void save(ProductOrder productOrder);
}
