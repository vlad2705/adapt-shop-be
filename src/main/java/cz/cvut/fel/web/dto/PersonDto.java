package cz.cvut.fel.web.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class PersonDto extends BasePersonDto {

    private String password;
    private Collection<RoleDto> roles;
    
    public PersonDto() {
        this.roles = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Long> getRoles() {
        return roles.stream().map(IdentificationDto::getId).collect(Collectors.toList());
    }
    
    public Collection<RoleDto> getRoleDtos() {
        return roles;
    }

    public void setRoles(Collection<Long> roles) {
        this.roles = roles.stream().map(RoleDto::new).collect(Collectors.toList());
    }
    
    public void setRoleDtos(Collection<RoleDto> roles) {
        this.roles = roles;
    }
}
