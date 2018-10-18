package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Address;

public interface AddressRepository extends IDAO<Address, Long> {
    void save(Address address);
}
