package cz.cvut.fel.web.dto;

public class PrivilegeDto extends NamedDto<Long> {
    public PrivilegeDto() {
    }
    
    public PrivilegeDto(long id) {
        setId(id);
    }
}
