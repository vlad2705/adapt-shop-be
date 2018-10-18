package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.web.dto.CategoryDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;

@Controller("categoryDetail")
@Scope("view")
@ELBeanName(value = "categoryDetail")
@Join(path = "/admin/category-detail", to = "/shop/category-detail.jsf")
@PreAuthorize("hasAuthority('category:detail')")
public class CategoryDetailController extends AbstractController {
    
    private final CategoryService categoryService;
    
    private CategoryDto category;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public CategoryDetailController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    public void initialize(String id) {
        if (this.category == null) {
            this.category = categoryService.getById(id);
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public CategoryDto getCategory() {
        return category;
    }
}
