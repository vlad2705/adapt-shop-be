package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.data.ProductData;
import cz.cvut.fel.web.dataModel.QueryDataModel;
import cz.cvut.fel.web.filter.ProductFilter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller("productList")
@Scope(value = "session")
@ELBeanName(value = "productList")
@Join(path = "/admin/products", to = "/shop/product-list.jsf")
@PreAuthorize("hasAuthority('product:view')")
public class ProductListController extends AbstractController {

    private final ProductService productService;
    
    private QueryDataModel<ProductData, ProductService, ProductFilter> products;
    private ProductData selectedProduct;
    private ProductFilter filter;

    @Autowired
    public ProductListController(ProductService productService) {
        this.productService = productService;
        filter = new ProductFilter();
    }
    
    @PostConstruct
    public void init() {
        products = new QueryDataModel<>(productService, filter);
    }

    public QueryDataModel getProducts() {
        return products;
    }

    public ProductData getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductData selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public ProductFilter getFilter() {
        return filter;
    }

    public void filter() {
        products = new QueryDataModel<>(productService, filter);
        selectedProduct = null;
    }

    public void reset() {
        filter = new ProductFilter();
        products = new QueryDataModel<>(productService, filter);
        selectedProduct = null;
    }
    
    @PreAuthorize("hasAuthority('product:create')")
    public String create() {
        try {
            return "/shop/product-form.jsf?faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('product:edit')")
    public String edit() {
        try {
            return "/shop/product-form.jsf?id=" + getSelectedProduct().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('product:detail')")
    public String detail() {
        try {
            return "/shop/product-detail.jsf?id=" + getSelectedProduct().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('product:picture')")
    public String pictures() {
        try {
            return "/shop/picture-form.jsf?id=" + getSelectedProduct().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('product:delete')")
    public void delete() {
        productService.delete(selectedProduct.getId());
        selectedProduct = null;
        init();
    }

    public void onRowSelect(SelectEvent event) {
        setSelectedProduct((ProductData) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        setSelectedProduct(null);
    }
}
