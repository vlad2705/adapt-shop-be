package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.OrderItemService;
import cz.cvut.fel.web.dto.OrderItemDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;

@Controller("orderItemDetail")
@Scope("view")
@ELBeanName(value = "orderItemDetail")
@Join(path = "/admin/order-item-detail", to = "/shop/order-item-detail.jsf")
@PreAuthorize("hasAuthority('order:item:detail')")
public class OrderItemDetailController  extends AbstractController {
    
    private final OrderItemService orderItemService;
    
    private OrderItemDto orderItem;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @ManagedProperty(value = "#{param.productOrderId}")
    private Long productOrderId;
    
    @Autowired
    public OrderItemDetailController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
    
    public void initialize(String id) {
        if (this.orderItem == null) {
            this.orderItem = orderItemService.getBaseDtoById(id);
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProductOrderId() {
        return productOrderId;
    }
    
    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }
    
    public OrderItemDto getOrderItem() {
        return orderItem;
    }
}
