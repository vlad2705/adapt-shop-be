package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.OrderItemService;
import cz.cvut.fel.service.ProductOrderService;
import cz.cvut.fel.web.dto.BaseProductOrderDto;
import cz.cvut.fel.web.dto.OrderItemDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;
import java.util.List;

@Controller("productOrderDetail")
@Scope("session")
@ELBeanName(value = "productOrderDetail")
@Join(path = "/admin/order-detail", to = "/shop/product-order-detail.jsf")
@PreAuthorize("hasAnyAuthority('order:detail')")
public class ProductOrderDetailController extends AbstractController {
    
    private final ProductOrderService productOrderService;
    private final OrderItemService orderItemService;
    
    private BaseProductOrderDto productOrder;
    private List<OrderItemDto> orderItems;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public ProductOrderDetailController(ProductOrderService productOrderService, OrderItemService orderItemService) {
        this.productOrderService = productOrderService;
        this.orderItemService = orderItemService;
    }
    
    public void initialize(String id) {
        this.productOrder = productOrderService.getById(id);
        this.orderItems = orderItemService.getByProductOrder(id);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BaseProductOrderDto getProductOrder() {
        return productOrder;
    }
    
    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }
}
