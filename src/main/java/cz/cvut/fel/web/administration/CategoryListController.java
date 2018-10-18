package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.web.data.CategoryData;
import cz.cvut.fel.web.dataModel.QueryDataModel;
import cz.cvut.fel.web.dto.CategoryDto;
import cz.cvut.fel.web.filter.CategoryFilter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@Controller("categoryList")
@Scope(value = "session")
@ELBeanName(value = "categoryList")
@Join(path = "/admin/categories", to = "/shop/category-list.jsf")
@PreAuthorize("hasAuthority('category:view')")
public class CategoryListController extends AbstractController {
    
    private final CategoryService categoryService;
    
    private TreeNode root;
    private TreeNode selectedCategory;
    private CategoryFilter filter;
    
    @Autowired
    public CategoryListController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        root = categoryService.getTree();
        filter = new CategoryFilter();
    }
    
    public TreeNode getRoot() {
        return root;
    }
    
    public TreeNode getSelectedCategory() {
        return selectedCategory;
    }
    
    public CategoryDto getSelectedCategoryData() {
        return (CategoryDto)this.selectedCategory.getData();
    }
    
    public void setSelectedCategory(TreeNode selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
    
    public CategoryFilter getFilter() {
        return filter;
    }
    
    public void filter() {
        root = categoryService.getByFilter(filter);
        selectedCategory = null;
    }
    
    public void reset() {
        root = categoryService.getTree();
        filter = new CategoryFilter();
        selectedCategory = null;
    }
    
    @PreAuthorize("hasAuthority('category:create')")
    public String create() {
        try {
            return "/shop/category-form.jsf?faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('category:edit')")
    public String edit() {
        try {
            return "/shop/category-form.jsf?id=" + getSelectedCategoryData().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('category:detail')")
    public String detail() {
        try {
            return "/shop/category-detail.jsf?id=" + getSelectedCategoryData().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('category:delete')")
    public void delete() {
        categoryService.delete(getSelectedCategoryData().getId());
        FacesMessage msg = new FacesMessage("Category deleted", getSelectedCategoryData().getName());
        selectedCategory = null;
        loadData();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
        setSelectedCategory(event.getTreeNode());
    }
    
    public void onNodeUnselect(NodeUnselectEvent event) {
        setSelectedCategory(null);
    }
}
