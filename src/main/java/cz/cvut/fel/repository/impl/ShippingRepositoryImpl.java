package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Shipping;
import cz.cvut.fel.repository.ShippingRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShippingRepositoryImpl extends AbstractDAO<Shipping, Long> implements ShippingRepository, ICanDelete<Shipping> {
    
    @Override
    public BusinessCheckResult checkCanDelete(Shipping shipping) {
        return new BusinessCheckSimpleResult(shipping != null, "This shipping does not exist!");
    }
}
