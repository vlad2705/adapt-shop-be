package cz.cvut.fel.model;

import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RATING")
public class Rating extends AbstractPersistable<Long> {
    
    @ManyToOne
    @JoinColumn(name = "PERSON")
    private Person person;
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT")
    private Product product;
    
    @Column
    private int rating;
    
    @Column
    private String description;
    
    @Column
    private LocalDateTime date;
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
