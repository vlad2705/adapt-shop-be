package cz.cvut.fel.model;

import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem extends AbstractPersistable<Long> {
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ORDER")
    private ProductOrder productOrder;
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT")
    private Product product;
    
    @Column(name = "QUANTITY")
    private int quantity;
    
    public ProductOrder getProductOrder() {
        return productOrder;
    }
    
    public void setProductOrder(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
