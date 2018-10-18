package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.AccountService;
import cz.cvut.fel.service.RoleService;
import cz.cvut.fel.web.dto.AccountDto;
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

@Controller("accountForm")
@Scope("view")
@ELBeanName(value = "accountForm")
@Join(path = "/admin/account-form", to = "/shop/account-form.jsf")
@PreAuthorize("hasAnyAuthority('account:create', 'account:edit')")
public class AccountFormController extends AbstractController {
    
    private final AccountService accountService;
    private final RoleService roleService;
    
    private AccountDto account;
    private List<RoleDto> roles;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public AccountFormController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }
    
    public void initialize(String id) {
        if (this.account == null) {
            this.account = accountService.getById(id);
            this.roles = roleService.getAll();
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String save() {
        accountService.save(account);
        account = null;
        return "/shop/account-list.xhtml?faces-redirect=true";
    }
    
    public AccountDto getAccount() {
        return account;
    }
    
    public List<SelectItem> getRoles() {
        return this.roles.stream().map(role ->
                new SelectItem(role.getId(), role.getName())).collect(Collectors.toList());
    }
}
