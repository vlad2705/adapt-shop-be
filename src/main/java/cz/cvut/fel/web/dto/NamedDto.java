package cz.cvut.fel.web.dto;

public class NamedDto<ID> extends IdentificationDto<ID> {
    private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
