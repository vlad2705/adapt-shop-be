package cz.cvut.fel.web.data;

public class PersonData extends BaseData {
    private String email;
    
    public PersonData() {
    }
    
    public PersonData(Object[] data) {
        this.email = (String) data[1];
        setId((Long) data[0]);
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
