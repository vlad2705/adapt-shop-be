package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Account;
import cz.cvut.fel.repository.AccountRepository;
import cz.cvut.fel.web.data.AccountData;
import cz.cvut.fel.web.filter.AccountFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountRepositoryImpl extends AbstractDAO<Account, Long> implements AccountRepository, ICanDelete<Account> {
    @Override
    public BusinessCheckResult checkCanDelete(Account account) {
        return new BusinessCheckSimpleResult(account != null, "This account does not exist!");
    }
    
    @Override
    public long findRowCount(AccountFilter filter) {
        Query query = getEntityManager().createQuery(filter.getRowCountQuery());
        filter.setParameters(query);
        return (long) query.getSingleResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<AccountData> findByFilter(AccountFilter filter) {
        Query query = getEntityManager().createQuery(filter.getQueryString());
        filter.setParameters(query);
        query.setFirstResult(filter.getFirst());
        query.setMaxResults(filter.getPageSize());
        return (List<AccountData>) query.getResultList().stream().map(data -> new AccountData((Object[]) data)).collect(Collectors.toList());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Account findByPersonId(long personId) {
        Query query = getEntityManager().createQuery("SELECT a FROM Account a WHERE a.person.id = :personId");
        query.setParameter("personId", personId);
        List<Account> accounts = query.getResultList();
        return accounts != null && accounts.size() > 0 ? accounts.get(0) : null;
    }
}
