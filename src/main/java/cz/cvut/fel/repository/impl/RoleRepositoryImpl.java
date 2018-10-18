package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Role;
import cz.cvut.fel.repository.RoleRepository;
import cz.cvut.fel.web.data.RoleData;
import cz.cvut.fel.web.filter.RoleFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl extends AbstractDAO<Role, Long> implements RoleRepository, ICanDelete<Role> {
    @Override
    public BusinessCheckResult checkCanDelete(Role role) {
        return new BusinessCheckSimpleResult(role != null, "This role does not exist!");
    }
    
    @Override
    public Role findByName(String name) {
        return null; // todo
    }
    
    @Override
    public long findRowCount(RoleFilter filter) {
        Query query = getEntityManager().createQuery(filter.getRowCountQuery());
        filter.setParameters(query);
        return (long) query.getSingleResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<RoleData> findByFilter(RoleFilter filter) {
        Query query = getEntityManager().createQuery(filter.getQueryString());
        filter.setParameters(query);
        query.setFirstResult(filter.getFirst());
        query.setMaxResults(filter.getPageSize());
        return (List<RoleData>) query.getResultList().stream().map(data -> new RoleData((Object[]) data)).collect(Collectors.toList());
    }
}
