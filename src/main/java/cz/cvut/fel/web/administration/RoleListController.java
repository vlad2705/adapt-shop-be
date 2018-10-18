package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.RoleService;
import cz.cvut.fel.web.data.RoleData;
import cz.cvut.fel.web.dataModel.QueryDataModel;
import cz.cvut.fel.web.filter.RoleFilter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Controller("roleList")
@Scope(value = "session")
@ELBeanName(value = "roleList")
@Join(path = "/admin/roles", to = "/shop/role-list.jsf")
@PreAuthorize("hasAuthority('role:view')")
public class RoleListController extends AbstractController implements Serializable {
    
    private final RoleService roleService;
    
    private QueryDataModel<RoleData, RoleService, RoleFilter> roles;
    private RoleData selectedRole;
    private RoleFilter filter;
    
    @Autowired
    public RoleListController(RoleService roleService) {
        this.roleService = roleService;
        filter = new RoleFilter();
    }
    
    @PostConstruct
    public void init() {
        roles = new QueryDataModel<>(roleService, filter);
    }
    
    public QueryDataModel getRoles() {
        return roles;
    }
    
    public RoleData getSelectedRole() {
        return selectedRole;
    }
    
    public void setSelectedRole(RoleData selectedRole) {
        this.selectedRole = selectedRole;
    }
    
    public RoleFilter getFilter() {
        return filter;
    }
    
    public void filter() {
        roles = new QueryDataModel<>(roleService, filter);
        selectedRole = null;
    }
    
    public void reset() {
        filter = new RoleFilter();
        roles = new QueryDataModel<>(roleService, filter);
        selectedRole = null;
    }
    
    @PreAuthorize("hasAuthority('role:create')")
    public String create() {
        try {
            return "/shop/role-form.jsf?faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('role:edit')")
    public String edit() {
        try {
            return "/shop/role-form.jsf?id=" + getSelectedRole().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('role:detail')")
    public String detail() {
        try {
            return "/shop/role-detail.jsf?id=" + getSelectedRole().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('role:delete')")
    public void delete() {
        roleService.delete(selectedRole.getId());
        selectedRole = null;
        init();
    }
    
    public void onRowSelect(SelectEvent event) {
        setSelectedRole((RoleData) event.getObject());
    }
    
    public void onRowUnselect(UnselectEvent event) {
        setSelectedRole(null);
    }
}
