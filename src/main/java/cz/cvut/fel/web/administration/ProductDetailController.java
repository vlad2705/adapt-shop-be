package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.service.PictureService;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.dto.PictureDto;
import cz.cvut.fel.web.dto.ProductDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.ByteArrayInputStream;
import java.util.List;

@Controller("productDetail")
@Scope("session")
@ELBeanName(value = "productDetail")
@Join(path = "/admin/product-detail", to = "/shop/product-detail.jsf")
//@PreAuthorize("hasAnyAuthority('product:detail')")
public class ProductDetailController extends AbstractController {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    private final PictureService pictureService;
    
    private ProductDto product;
    private TreeNode root;
    private List<PictureDto> pictures;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public ProductDetailController(ProductService productService, CategoryService categoryService, PictureService pictureService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.pictureService = pictureService;
    }
    
    public void initialize(String id) {
        this.product = productService.getById(id);
        this.root = this.product.getCategoryData() == null ? categoryService.getTree() : categoryService.getTree(this.product.getCategoryData().getId());
        pictures = pictureService.getByProduct(id);
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        return "/shop/login.jsf?faces-redirect=true";
    }
    
    public String cancel() {
        return "/shop/product-list.xhtml?faces-redirect=true";
    }
    
    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        String id = context.getExternalContext().getRequestParameterMap().get("id");
        PictureDto pictureDto = pictureService.getById(id);
        return new DefaultStreamedContent(new ByteArrayInputStream(pictureDto.getContent()));
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
    
    public List<PictureDto> getPictures() {
        return pictures;
    }
    
    public ProductDto getProduct() {
        return product;
    }
}
