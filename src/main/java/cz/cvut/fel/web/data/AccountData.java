package cz.cvut.fel.web.data;

public class AccountData extends BaseData {
    private String firstName;
    private String lastName;
    private String email;
    
    public AccountData() {
    }
    
    public AccountData(Object[] data) {
        this.firstName = (String) data[1];
        this.lastName = (String) data[2];
        this.email = (String) data[3];
        setId((Long) data[0]);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
