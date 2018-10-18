package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.RoleService;
import cz.cvut.fel.web.dto.RoleDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;

@Controller("roleDetail")
@Scope("view")
@ELBeanName(value = "roleDetail")
@Join(path = "/admin/role-detail", to = "/shop/role-detail.jsf")
@PreAuthorize("hasAuthority('role:detail')")
public class RoleDetailController extends AbstractController {
    
    private final RoleService roleService;
    
    private RoleDto role;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public RoleDetailController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    public void initialize(String id) {
        if (this.role == null) {
            this.role = roleService.getById(id);
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public RoleDto getRole() {
        return role;
    }
}
