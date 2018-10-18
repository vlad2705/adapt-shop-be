package cz.cvut.fel.web.dto;

public class OrderItemDto extends IdentificationDto<Long> {
    private BaseProductDto product;
    private Integer quantity;
    
    public OrderItemDto() {
        this.quantity = 1;
    }
    
    public Long getProduct() {
        return product != null ? product.getId() : null;
    }
    
    public BaseProductDto getProductDto() {
        return product;
    }
    
    public void setProduct(Long productId) {
        this.product = new BaseProductDto();
        product.setId(productId);
    }
    
    public void setProductDto(BaseProductDto product) {
        this.product = product;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
