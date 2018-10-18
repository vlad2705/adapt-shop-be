package cz.cvut.fel.web.administration;

import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.web.dto.CategoryDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;

@Controller("categoryForm")
@Scope("view")
@ELBeanName(value = "categoryForm")
@Join(path = "/admin/category-form", to = "/shop/category-form.jsf")
@PreAuthorize("hasAnyAuthority('category:create', 'category:edit')")
public class CategoryFormController {
    private final CategoryService categoryService;
    
    private CategoryDto category;
    private TreeNode root;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public CategoryFormController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    public void initialize(String id) {
        if (this.category == null) {
            this.category = categoryService.getById(id);
            root = this.category.getParent() == null ? categoryService.getTree() : categoryService.getTree(this.category.getParentData().getId());
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
    
    public TreeNode getRoot() {
        return root;
    }
    
    public String save() {
        categoryService.save(category);
        category = new CategoryDto();
        return "/shop/category-list.xhtml?faces-redirect=true";
    }
}
