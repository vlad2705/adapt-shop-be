package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.dto.ProductDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;

@Controller("productForm")
@Scope("view")
@ELBeanName(value = "productForm")
@Join(path = "/admin/product-form", to = "/shop/product-form.jsf")
@PreAuthorize("hasAnyAuthority('product:create', 'product:edit')")
public class ProductFormController extends AbstractController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private ProductDto product;
    private TreeNode root;

    @ManagedProperty(value = "#{param.id}")
    private Long id;

    @Autowired
    public ProductFormController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    public void initialize(String id) {
        if (this.product == null) {
            this.product = productService.getById(id);
            root = this.product.getCategoryData() == null ? categoryService.getTree() : categoryService.getTree(this.product.getCategoryData().getId());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public TreeNode getRoot() {
        return root;
    }

    public String save() {
        productService.save(product);
        product = null;
        return "/shop/product-list.xhtml?faces-redirect=true";
    }
    
    public String cancel() {
        return "/shop/product-list.xhtml?faces-redirect=true";
    }

    public ProductDto getProduct() {
        return product;
    }
}
