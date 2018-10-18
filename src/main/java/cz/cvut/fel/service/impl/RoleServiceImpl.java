package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Role;
import cz.cvut.fel.repository.RoleRepository;
import cz.cvut.fel.service.PrivilegeService;
import cz.cvut.fel.service.RoleService;
import cz.cvut.fel.web.data.RoleData;
import cz.cvut.fel.web.dto.RoleDto;
import cz.cvut.fel.web.filter.RoleFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;
    private final PrivilegeService privilegeService;
    
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PrivilegeService privilegeService) {
        this.roleRepository = roleRepository;
        this.privilegeService = privilegeService;
    }
    
    @Override
    public RoleDto convertToDto(Role role) {
        if (role != null) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDto.setPrivilegeDtos(role.getPrivileges().stream().map(privilegeService::convertToDto).collect(Collectors.toList()));
            return roleDto;
        }
        return null;
    }
    
    public Role convertToModel(RoleDto roleDto) {
        if (roleDto != null) {
            return roleDto.getId() != null
                    ? convertToModel(roleRepository.findById(roleDto.getId()), roleDto)
                    : convertToModel(new Role(), roleDto);
        }
        return null;
    }
    
    @Override
    public Role convertToModel(Role role, RoleDto roleDto) {
        if (roleDto != null) {
            role.setName(roleDto.getName());
            role.setPrivileges(roleDto.getPrivilegeDtos().stream().map(privilegeService::convertToModel).collect(Collectors.toList()));
            return role;
        }
        return null;
    }
    
    @Override
    public RoleDto save(RoleDto roleDto) {
        if (roleDto != null) {
            Role role = convertToModel(roleDto);
            roleRepository.save(role);
            return convertToDto(role);
        }
        return null;
    }
    
    @Override
    public RoleDto getById(long id) {
        return convertToDto(roleRepository.findById(id));
    }
    
    @Override
    public RoleDto getById(String id) {
        return id == null || id.isEmpty() ? new RoleDto() : getById(Long.valueOf(id));
    }
    
    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    @Override
    public long getRowCount(RoleFilter filter) {
        return roleRepository.findRowCount(filter);
    }
    
    @Override
    public List<RoleData> getByFilter(RoleFilter filter) {
        return roleRepository.findByFilter(filter);
    }
    
    @Override
    public RoleData getDataById(String id) {
        return id == null || id.isEmpty() ? new RoleData() : getDataById(Long.valueOf(id));
    }
    
    @Override
    public RoleData getDataById(long id) {
        return convertToData(roleRepository.findById(id));
    }
    
    @Override
    public RoleData convertToData(Role role) {
        if (role != null) {
            RoleData roleData = new RoleData();
            roleData.setId(role.getId());
            role.setName(role.getName());
            return roleData;
        }
        return null;
    }
    
    @Override
    public void delete(long id) {
        this.roleRepository.delete(this.roleRepository.findById(id));
    }
}
