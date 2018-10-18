package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.businesschecks.BusinessCheckResult;
import cz.cvut.fel.asf.businesschecks.BusinessCheckSimpleResult;
import cz.cvut.fel.asf.persistence.ICanDelete;
import cz.cvut.fel.asf.persistence.jpa.AbstractDAO;
import cz.cvut.fel.model.Person;
import cz.cvut.fel.repository.AccountRepository;
import cz.cvut.fel.repository.PersonRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PersonRepositoryImpl extends AbstractDAO<Person, Long> implements PersonRepository, ICanDelete<Person> {
    
    private final AccountRepository accountRepository;
    
    public PersonRepositoryImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public BusinessCheckResult checkCanDelete(Person person) {
        return new BusinessCheckSimpleResult(
                person != null && accountRepository.findByPersonId(person.getId()) == null,
                "This product does not exist!"
        );
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Person findByEmail(String email) {
        Query query = getEntityManager().createQuery("SELECT p FROM Person p WHERE p.email = :email");
        query.setParameter("email", email);
        List<Person> persons = query.getResultList();
        return persons != null && persons.size() > 0 ? persons.get(0) : null;
    }
}
