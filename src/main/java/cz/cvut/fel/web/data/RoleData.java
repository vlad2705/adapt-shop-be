package cz.cvut.fel.web.data;

public class RoleData extends BaseData {
    private String name;
    
    public RoleData() {
    }
    
    public RoleData(Object[] data) {
        this.name = (String) data[1];
        setId((Long) data[0]);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
