package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.OrderItemService;
import cz.cvut.fel.service.OrderStatusService;
import cz.cvut.fel.service.ProductOrderService;
import cz.cvut.fel.web.dto.BaseProductOrderDto;
import cz.cvut.fel.web.dto.OrderItemDto;
import cz.cvut.fel.web.dto.OrderStatusDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.stream.Collectors;

@Controller("productOrderForm")
@Scope("view")
@ELBeanName(value = "productOrderForm")
@Join(path = "/admin/order-form", to = "/shop/product-order-form.jsf")
@PreAuthorize("hasAnyAuthority('order:create', 'order:edit')")
public class ProductOrderFormController extends AbstractController {
    
    private final ProductOrderService productOrderService;
    private final OrderItemService orderItemService;
    private final OrderStatusService orderStatusService;
    
    private BaseProductOrderDto productOrder;
    private List<OrderItemDto> orderItems;
    private OrderItemDto selectedOrderItem;
    private List<OrderStatusDto> orderStatuses;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public ProductOrderFormController(ProductOrderService productOrderService, OrderItemService orderItemService, OrderStatusService orderStatusService) {
        this.productOrderService = productOrderService;
        this.orderItemService = orderItemService;
        this.orderStatusService = orderStatusService;
    }
    
    public void initialize(String id) {
        if (this.productOrder == null) {
            this.productOrder = productOrderService.getById(id);
            this.orderItems = orderItemService.getByProductOrder(id);
            this.orderStatuses = orderStatusService.getAll();
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String save() {
        productOrderService.save(productOrder);
        productOrder = null;
        return "/shop/product-order-list.xhtml?faces-redirect=true";
    }
    
    public List<SelectItem> getOrderStatuses() { // FIXME: move converter to service
        return orderStatuses.stream().map(status -> new SelectItem(status.getId(), status.getName())).collect(Collectors.toList());
    }
    
    public BaseProductOrderDto getProductOrder() {
        return productOrder;
    }
    
    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }
    
    public OrderItemDto getSelectedOrderItem() {
        return selectedOrderItem;
    }
    
    public void setSelectedOrderItem(OrderItemDto selectedOrderItem) {
        this.selectedOrderItem = selectedOrderItem;
    }
    
    @PreAuthorize("hasAuthority('order:item:create')")
    public String create() {
        try {
            return "/shop/order-item-form.jsf?productOrderId=" + getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('order:item:edit')")
    public String edit() {
        try {
            return "/shop/order-item-form.jsf?id=" + getSelectedOrderItem().getId()
                    + "&amp;productOrderId=" + getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('order:item:detail')")
    public String detail() {
        try {
            return "/shop/order-item-detail.jsf?id=" + getSelectedOrderItem().getId()
                    + "&amp;productOrderId=" + getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('order:item:delete')")
    public void delete() {
        orderItemService.delete(getSelectedOrderItem().getId());
        selectedOrderItem = null;
        orderItems = orderItemService.getAll();
    }
    
    public void onRowSelect(SelectEvent event) {
        setSelectedOrderItem((OrderItemDto) event.getObject());
    }
    
    public void onRowUnselect(UnselectEvent event) {
        setSelectedOrderItem(null);
    }
}
