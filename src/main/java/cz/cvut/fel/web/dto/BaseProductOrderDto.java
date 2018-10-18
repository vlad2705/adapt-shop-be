package cz.cvut.fel.web.dto;

public class BaseProductOrderDto extends IdentificationDto<Long> {
    private OrderStatusDto orderStatus;
    
    public Long getOrderStatus() {
        return orderStatus != null ? orderStatus.getId() : null;
    }
    
    public OrderStatusDto getOrderStatusDto() {
        return orderStatus;
    }
    
    public void setOrderStatus(Long id) {
        this.orderStatus = new OrderStatusDto();
        orderStatus.setId(id);
    }
    
    public void setOrderStatusDto(OrderStatusDto orderStatus) {
        this.orderStatus = orderStatus;
    }
}
