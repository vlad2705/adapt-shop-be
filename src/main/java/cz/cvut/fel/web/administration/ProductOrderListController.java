package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.OrderStatusService;
import cz.cvut.fel.service.ProductOrderService;
import cz.cvut.fel.web.data.ProductOrderData;
import cz.cvut.fel.web.dataModel.QueryDataModel;
import cz.cvut.fel.web.dto.OrderStatusDto;
import cz.cvut.fel.web.filter.ProductOrderFilter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Controller("productOrderList")
@Scope(value = "session")
@ELBeanName(value = "productOrderList")
@Join(path = "/admin/orders", to = "/shop/product-order-list.jsf")
@PreAuthorize("hasAuthority('order:view')")
public class ProductOrderListController extends AbstractController implements Serializable {
    
    private final ProductOrderService productOrderService;
    private final OrderStatusService orderStatusService;
    
    private QueryDataModel<ProductOrderData, ProductOrderService, ProductOrderFilter> productOrders;
    private ProductOrderData selectedProductOrder;
    private ProductOrderFilter filter;
    private List<OrderStatusDto> orderStatuses;
    
    @Autowired
    public ProductOrderListController(ProductOrderService productOrderService, OrderStatusService orderStatusService) {
        this.productOrderService = productOrderService;
        this.orderStatusService = orderStatusService;
        filter = new ProductOrderFilter();
    }
    
    @PostConstruct
    public void init() {
        productOrders = new QueryDataModel<>(productOrderService, filter);
        orderStatuses = orderStatusService.getAll();
    }
    
    public QueryDataModel getProductOrders() {
        return productOrders;
    }
    
    public ProductOrderData getSelectedProductOrder() {
        return selectedProductOrder;
    }
    
    public void setSelectedProductOrder(ProductOrderData selectedProductOrder) {
        this.selectedProductOrder = selectedProductOrder;
    }
    
    public ProductOrderFilter getFilter() {
        return filter;
    }
    
    public void filter() {
        productOrders = new QueryDataModel<>(productOrderService, filter);
        selectedProductOrder = null;
    }
    
    public void reset() {
        filter = new ProductOrderFilter();
        productOrders = new QueryDataModel<>(productOrderService, filter);
        selectedProductOrder = null;
    }
    
    @PreAuthorize("hasAuthority('order:edit')")
    public String edit() {
        try {
            return "/shop/product-order-form.jsf?id=" + getSelectedProductOrder().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('order:detail')")
    public String detail() {
        try {
            return "/shop/product-order-detail.jsf?id=" + getSelectedProductOrder().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('order:delete')")
    public void delete() {
        productOrderService.delete(selectedProductOrder.getId());
        selectedProductOrder = null;
        init();
    }
    
    public void onRowSelect(SelectEvent event) {
        setSelectedProductOrder((ProductOrderData) event.getObject());
    }
    
    public void onRowUnselect(UnselectEvent event) {
        setSelectedProductOrder(null);
    }
    
    public List<SelectItem> getOrderStatuses() {
        return orderStatuses.stream().map(orderStatus ->
                new SelectItem(orderStatus.getId(), orderStatus.getName())).collect(Collectors.toList());
    }
}
