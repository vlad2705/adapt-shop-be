package cz.cvut.fel.web.data;

public class CategoryData extends BaseData {
    private String name;
    
    public CategoryData() {
    }
    
    public CategoryData(Object[] data) {
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
