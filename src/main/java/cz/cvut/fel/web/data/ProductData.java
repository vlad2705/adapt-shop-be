package cz.cvut.fel.web.data;

import java.math.BigDecimal;

public class ProductData extends BaseData {
    private String name;
    private BigDecimal price;
    private String category;

    public ProductData() {
    }

    public ProductData(Object[] data) {
        this.name = (String) data[1];
        this.price = (BigDecimal) data[2];
        this.category = (String) data[3];
        setId((Long) data[0]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}
