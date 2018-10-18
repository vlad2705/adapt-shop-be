package cz.cvut.fel.web.administration;

import cz.cvut.fel.asf.web.primefaces.AbstractController;
import cz.cvut.fel.service.AccountService;
import cz.cvut.fel.web.data.AccountData;
import cz.cvut.fel.web.dataModel.QueryDataModel;
import cz.cvut.fel.web.filter.AccountFilter;
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

@Controller("accountList")
@Scope(value = "session")
@ELBeanName(value = "accountList")
@Join(path = "/admin/accounts", to = "/shop/account-list.jsf")
@PreAuthorize("hasAuthority('account:view')")
public class AccountListController extends AbstractController implements Serializable {
    
    private final AccountService accountService;
    
    private QueryDataModel<AccountData, AccountService, AccountFilter> accounts;
    private AccountData selectedAccount;
    private AccountFilter filter;
    
    @Autowired
    public AccountListController(AccountService accountService) {
        this.accountService = accountService;
        filter = new AccountFilter();
    }
    
    @PostConstruct
    public void init() {
        accounts = new QueryDataModel<>(accountService, filter);
    }
    
    public QueryDataModel getAccounts() {
        return accounts;
    }
    
    public AccountData getSelectedAccount() {
        return selectedAccount;
    }
    
    public void setSelectedAccount(AccountData selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
    
    public AccountFilter getFilter() {
        return filter;
    }
    
    public void filter() {
        accounts = new QueryDataModel<>(accountService, filter);
        selectedAccount = null;
    }
    
    public void reset() {
        filter = new AccountFilter();
        accounts = new QueryDataModel<>(accountService, filter);
        selectedAccount = null;
    }
    
    @PreAuthorize("hasAuthority('account:create')")
    public String create() {
        try {
            return "/shop/account-form.jsf?faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('account:edit')")
    public String edit() {
        try {
            return "/shop/account-form.jsf?id=" + getSelectedAccount().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('account:detail')")
    public String detail() {
        try {
            return "/shop/account-detail.jsf?id=" + getSelectedAccount().getId() + "&amp;faces-redirect=true";
        } catch (NullPointerException error) {
            return null;
        }
    }
    
    @PreAuthorize("hasAuthority('account:delete')")
    public void delete() {
        accountService.delete(selectedAccount.getId());
        selectedAccount = null;
        init();
    }
    
    public void onRowSelect(SelectEvent event) {
        setSelectedAccount((AccountData) event.getObject());
    }
    
    public void onRowUnselect(UnselectEvent event) {
        setSelectedAccount(null);
    }
}
