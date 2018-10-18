package cz.cvut.fel.model;

import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Any type of product or service offered in the shop
 */

@Entity
@Table(name = "PRODUCT")
public class Product extends AbstractPersistable<Long> {
    
    public Product() {}
    
    public Product(String name, BigDecimal price, Category category, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }
    
    @Column
    private String name;
    
    @Column
    private BigDecimal price;
    
    @Column
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "CATEGORY")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "PICTURE")
    private Picture picture;
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public Picture getPicture() {
        return picture;
    }
    
    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
