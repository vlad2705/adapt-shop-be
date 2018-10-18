package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.OrderStatus;
import cz.cvut.fel.repository.OrderStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderStatusRepositoryImpl extends AbstractDAO<OrderStatus, Long> implements OrderStatusRepository, ICanDelete<OrderStatus> {
    @Override
    public BusinessCheckResult checkCanDelete(OrderStatus orderStatus) {
        return new BusinessCheckSimpleResult(false, "You can not delete order statuses");
    }
}
