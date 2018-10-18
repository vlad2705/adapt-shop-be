package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Shipping;

public interface ShippingRepository extends IDAO<Shipping, Long> {
    
    void save(Shipping shipping);
}
