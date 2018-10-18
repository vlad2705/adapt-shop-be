package cz.cvut.fel.model;

import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_STATUS")
public class OrderStatus extends AbstractPersistable<Long> {

    @Column(name = "NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
