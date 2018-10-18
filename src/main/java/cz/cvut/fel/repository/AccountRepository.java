package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Account;
import cz.cvut.fel.web.data.AccountData;
import cz.cvut.fel.web.filter.AccountFilter;

import java.util.List;

public interface AccountRepository extends IDAO<Account, Long> {
    long findRowCount(AccountFilter filter);
    
    List<AccountData> findByFilter(AccountFilter filter);
    
    Account findByPersonId(long personId);
    
    void save(Account account);
}
