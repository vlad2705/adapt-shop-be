package cz.cvut.fel.web.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class RoleDto extends NamedDto<Long> {
    private Collection<PrivilegeDto> privileges;
    
    public RoleDto() {
        this.privileges = new ArrayList<>();
    }
    
    public RoleDto(long id) {
        setId(id);
    }
    
    public Collection<Long> getPrivileges() {
        return privileges.stream().map(IdentificationDto::getId).collect(Collectors.toList());
    }
    
    public Collection<PrivilegeDto> getPrivilegeDtos() {
        return privileges;
    }
    
    public void setPrivileges(Collection<Long> privileges) {
        this.privileges = privileges.stream().map(PrivilegeDto::new).collect(Collectors.toList());
    }
    
    public void setPrivilegeDtos(Collection<PrivilegeDto> privileges) {
        this.privileges = privileges;
    }
}
