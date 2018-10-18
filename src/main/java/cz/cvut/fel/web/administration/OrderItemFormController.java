package cz.cvut.fel.web.administration;

import cz.cvut.fel.service.OrderItemService;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.dto.BaseProductDto;
import cz.cvut.fel.web.dto.OrderItemDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;
import java.util.List;

@Controller("orderItemForm")
@Scope("view")
@ELBeanName(value = "orderItemForm")
@Join(path = "/admin/order-item-form", to = "/shop/order-item-form.jsf")
@PreAuthorize("hasAnyAuthority('order:item:create', 'order:item:edit')")
public class OrderItemFormController {
    private final OrderItemService orderItemService;
    private final ProductService productService;
    
    private OrderItemDto orderItem;
    private List<BaseProductDto> products;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @ManagedProperty(value = "#{param.productOrderId}")
    private Long productOrderId;
    
    @Autowired
    public OrderItemFormController(OrderItemService orderItemService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.productService = productService;
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
    
    public String save() {
        orderItemService.save(orderItem, productOrderId);
        orderItem = null;
        return "/shop/product-order-form.xhtml?id=" + getProductOrderId() + "&amp;faces-redirect=true";
    }
    
    public String cancel() {
        return "/shop/product-order-form.xhtml?id=" + getProductOrderId() + "&amp;faces-redirect=true";
    }
    
    public OrderItemDto getOrderItem() {
        return orderItem;
    }
    
    public List<BaseProductDto> getProducts() {
        return products;
    }
    
    public List<BaseProductDto> completeProduct(String query) {
        return productService.getByName(query);
    }
}
