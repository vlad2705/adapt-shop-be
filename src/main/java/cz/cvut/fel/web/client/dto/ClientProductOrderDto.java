package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.IdentificationDto;

import java.util.List;

public class ClientProductOrderDto extends IdentificationDto<Long> {
    private List<CartProductDto> products;
    private ClientShippingDto shipping;
    
    public List<CartProductDto> getProducts() {
        return products;
    }
    
    public void setProducts(List<CartProductDto> products) {
        this.products = products;
    }
    
    public ClientShippingDto getShipping() {
        return shipping;
    }
    
    public void setShipping(ClientShippingDto shipping) {
        this.shipping = shipping;
    }
}
