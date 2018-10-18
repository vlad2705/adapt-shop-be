package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Privilege;
import cz.cvut.fel.repository.PrivilegeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrivilegeRepositoryImpl extends AbstractDAO<Privilege, Long> implements PrivilegeRepository, ICanDelete<Privilege> {
    @Override
    public BusinessCheckResult checkCanDelete(Privilege privilege) {
        return new BusinessCheckSimpleResult(false, "You can not delete the privilege!");
    }
    
    @Override
    public Privilege findByName(String name) {
        return null; // todo
    }
}
