package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.NamedDto;

import java.math.BigDecimal;

public class ClientProductDto extends NamedDto<Long> {
    private BigDecimal price;
    private ClientPictureDto picture;
    
    public ClientProductDto() {
    }
    
    public ClientProductDto(Object[] data) {
        this.price = (BigDecimal) data[2];
        setId((Long) data[0]);
        setName((String) data[1]);
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public ClientPictureDto getPicture() {
        return picture;
    }
    
    public void setPicture(ClientPictureDto picture) {
        this.picture = picture;
    }
}
