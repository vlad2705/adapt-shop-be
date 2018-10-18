package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.PrivilegeService;
import cz.cvut.fel.service.RoleService;
import cz.cvut.fel.web.dto.PrivilegeDto;
import cz.cvut.fel.web.dto.RoleDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.stream.Collectors;

@Controller("roleForm")
@Scope("view")
@ELBeanName(value = "roleForm")
@Join(path = "/admin/role-form", to = "/shop/role-form.jsf")
@PreAuthorize("hasAnyAuthority('role:create', 'role:edit')")
public class RoleFormController extends AbstractController {
    
    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    
    private RoleDto role;
    private List<PrivilegeDto> privileges;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public RoleFormController(RoleService roleService, PrivilegeService privilegeService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }
    
    public void initialize(String id) {
        if (this.role == null) {
            this.role = roleService.getById(id);
            this.privileges = privilegeService.getAll();
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String save() {
        roleService.save(role);
        role = null;
        return "/shop/role-list.xhtml?faces-redirect=true";
    }
    
    public RoleDto getRole() {
        return role;
    }
    
    public List<SelectItem> getPrivileges() {
        return privileges.stream().map(privilege ->
                new SelectItem(privilege.getId(), privilege.getName())).collect(Collectors.toList());
    }
}
