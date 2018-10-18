package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Address;
import cz.cvut.fel.repository.AddressRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl extends AbstractDAO<Address, Long> implements AddressRepository, ICanDelete<Address> {
    @Override
    public BusinessCheckResult checkCanDelete(Address address) {
        return new BusinessCheckSimpleResult(address != null, "This address does not exist!");
    }
}
