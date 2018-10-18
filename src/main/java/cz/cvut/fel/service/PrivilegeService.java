package cz.cvut.fel.service;

import cz.cvut.fel.model.Privilege;
import cz.cvut.fel.web.dto.PrivilegeDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface PrivilegeService {
    PrivilegeDto convertToDto(Privilege privilege);
    
    Privilege convertToModel(PrivilegeDto privilegeDto);
    
    Privilege convertToModel(long id);
    
    List<PrivilegeDto> getAll();
    
    String[] getAllNames();
    
    List<GrantedAuthority> getAllAuthorities();
}
