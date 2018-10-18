package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.NamedDto;

import java.math.BigDecimal;
import java.util.List;

public class ClientProductDetailDto extends NamedDto<Long> {
    private BigDecimal price;
    private String description;
    private Double rating;
    private List<ClientPictureDto> pictures;
    
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
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public List<ClientPictureDto> getPictures() {
        return pictures;
    }
    
    public void setPictures(List<ClientPictureDto> pictures) {
        this.pictures = pictures;
    }
}
