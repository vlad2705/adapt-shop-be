package cz.cvut.fel.web.dto;

public class BasePersonDto extends IdentificationDto<Long> {
    private String email;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
