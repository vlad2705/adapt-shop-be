package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.IdentificationDto;

public class ClientDto extends IdentificationDto<Long> {
    private String email;
    private String password;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
