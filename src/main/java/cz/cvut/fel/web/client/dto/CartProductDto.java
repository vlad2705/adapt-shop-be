package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.IdentificationDto;

public class CartProductDto extends IdentificationDto<Long> {
    private ClientProductDto product;
    private int quantity;
    
    public ClientProductDto getProduct() {
        return product;
    }
    
    public void setProduct(ClientProductDto product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
