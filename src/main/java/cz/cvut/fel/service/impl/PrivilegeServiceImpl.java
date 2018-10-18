package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Privilege;
import cz.cvut.fel.repository.PrivilegeRepository;
import cz.cvut.fel.service.PrivilegeService;
import cz.cvut.fel.web.dto.PrivilegeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {
    
    private final PrivilegeRepository privilegeRepository;
    
    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }
    
    @Override
    public PrivilegeDto convertToDto(Privilege privilege) {
        if (privilege != null) {
            PrivilegeDto privilegeDto = new PrivilegeDto();
            privilegeDto.setId(privilege.getId());
            privilegeDto.setName(privilege.getName());
            return privilegeDto;
        }
        return null;
    }
    
    @Override
    public Privilege convertToModel(PrivilegeDto privilegeDto) {
        if (privilegeDto != null && privilegeDto.getId() != null) {
            return privilegeRepository.findById(privilegeDto.getId());
        }
        return null;
    }
    
    @Override
    public Privilege convertToModel(long id) {
        return privilegeRepository.findById(id);
    }
    
    @Override
    public List<PrivilegeDto> getAll() {
        return privilegeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    @Override
    public String[] getAllNames() {
        List<Privilege> privileges = privilegeRepository.findAll();
        if (privileges != null && !privileges.isEmpty()) {
            String[] names = new String[privileges.size()];
            for (int i = 0; i < names.length; ++i) {
                names[i] = privileges.get(i).getName();
            }
            return names;
        }
        return new String[0];
    }
    
    @Override
    public List<GrantedAuthority> getAllAuthorities() {
        return privilegeRepository.findAll().stream().map(
                privilege -> new SimpleGrantedAuthority(privilege.getName())).collect(Collectors.toList()
        );
    }
}
