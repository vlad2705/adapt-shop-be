package cz.cvut.fel.model;

import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_ORDER")
public class ProductOrder extends AbstractPersistable<Long> {
    
    @Column(name = "SESSION_ID")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "PERSON")
    private Person person;
    
    @ManyToOne
    @JoinColumn(name = "SHIPPING")
    private Shipping shipping;

    @ManyToOne
    @JoinColumn(name = "ORDER_STATUS")
    private OrderStatus orderStatus;
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }
    
    public Shipping getShipping() {
        return shipping;
    }
    
    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }
    
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
