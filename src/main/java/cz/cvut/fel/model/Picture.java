package cz.cvut.fel.model;

import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "PICTURE")
public class Picture extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "PRODUCT")
    private Product product;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "CONTENT")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;
    
    @Column(name = "TYPE")
    private String type;

    @Column(name = "SIZE")
    private long size;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
