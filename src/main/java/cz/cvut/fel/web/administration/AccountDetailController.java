package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.AccountService;
import cz.cvut.fel.web.dto.AccountDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedProperty;

@Controller("accountDetail")
@Scope("view")
@ELBeanName(value = "accountDetail")
@Join(path = "/admin/account-detail", to = "/shop/account-detail.jsf")
@PreAuthorize("hasAuthority('account:detail')")
public class AccountDetailController extends AbstractController {
    
    private final AccountService accountService;
    
    private AccountDto account;
    
    @ManagedProperty(value = "#{param.id}")
    private Long id;
    
    @Autowired
    public AccountDetailController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    public void initialize(String id) {
        if (this.account == null) {
            this.account = accountService.getById(id);
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public AccountDto getAccount() {
        return account;
    }
}
