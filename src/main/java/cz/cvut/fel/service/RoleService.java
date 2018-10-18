package cz.cvut.fel.service;

import cz.cvut.fel.model.Role;
import cz.cvut.fel.web.data.RoleData;
import cz.cvut.fel.web.dto.RoleDto;
import cz.cvut.fel.web.filter.RoleFilter;

import java.util.List;

public interface RoleService extends BaseService<RoleData, RoleFilter> {
    RoleData getDataById(long id);
    
    RoleData convertToData(Role role);
    
    void delete(long id);
    
    RoleDto convertToDto(Role role);
    
    Role convertToModel(RoleDto roleDto);
    
    Role convertToModel(Role role, RoleDto roleDto);
    
    RoleDto save(RoleDto roleDto);
    
    RoleDto getById(long id);
    
    RoleDto getById(String id);
    
    List<RoleDto> getAll();
}
