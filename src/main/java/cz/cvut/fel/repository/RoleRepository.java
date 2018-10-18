package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Role;
import cz.cvut.fel.web.data.RoleData;
import cz.cvut.fel.web.filter.RoleFilter;

import java.util.List;

public interface RoleRepository extends IDAO<Role, Long> {
    void save(Role role);
    
    Role findByName(String name);
    
    long findRowCount(RoleFilter filter);
    
    List<RoleData> findByFilter(RoleFilter filter);
}
