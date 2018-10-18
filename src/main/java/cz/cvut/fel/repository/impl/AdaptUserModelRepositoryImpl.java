package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.adapt.gomawe.IUser;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserModelAttribute;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserModelAttributeDAOImpl;
import cz.cvut.fel.asf.persistence.IPersistable;
import cz.cvut.fel.asf.tools.ArgsChecker;
import cz.cvut.fel.repository.AdaptUserModelRepository;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class AdaptUserModelRepositoryImpl extends TableUserModelAttributeDAOImpl implements AdaptUserModelRepository {
    
    @Override
    public List<String> findByDomainObjectNameAndName(IUser user, String domainModelClassName, String attributeName) {
        (new ArgsChecker()).checkRequiredParameters("user", user, "attributeName", attributeName);
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<String> c = cb.createQuery(String.class);
        Root<TableUserModelAttribute> p = c.from(TableUserModelAttribute.class);
        c.orderBy(cb.desc(p.get("attributeValue")));
        c.multiselect(p.get("domainModelInstanceId"));
        return this.getEntityManager().createQuery(
                c.where(this.getCriteriaBuilder()
                        .and(
                                this.getCriteriaBuilder().equal(p.get("idUser"), user.getId().toString()),
                                this.getCriteriaBuilder().equal(p.get("domainModelClassName"), domainModelClassName),
                                this.getCriteriaBuilder().equal(p.get("attributeName"), attributeName)
                        )
                )
        ).getResultList();
    }
    
    @Override
    public List<String> findByDomainObjectNameAndName(IUser user, String domainModelClassName, String attributeName, BaseClientFilter filter) {
        (new ArgsChecker()).checkRequiredParameters("user", user, "attributeName", attributeName);
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<String> c = cb.createQuery(String.class);
        Root<TableUserModelAttribute> p = c.from(TableUserModelAttribute.class);
        c.orderBy(cb.desc(p.get("attributeValue")));
        c.multiselect(p.get("domainModelInstanceId"));
        return this.getEntityManager().createQuery(
                c.where(this.getCriteriaBuilder()
                        .and(
                                this.getCriteriaBuilder().equal(p.get("idUser"), user.getId().toString()),
                                this.getCriteriaBuilder().equal(p.get("domainModelClassName"), domainModelClassName),
                                this.getCriteriaBuilder().equal(p.get("attributeName"), attributeName)
                        )
                )
        ).setFirstResult(filter.getFirst()).setMaxResults(filter.getPageSize()).getResultList();
    }
    
    @Override
    public long findRowCount(IUser user, String domainModelClassName, String attributeName) {
        (new ArgsChecker()).checkRequiredParameters("user", user, "attributeName", attributeName);
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Long> c = cb.createQuery(Long.class);
        Root<TableUserModelAttribute> p = c.from(TableUserModelAttribute.class);
        c.select(cb.count(p));
        return this.getEntityManager().createQuery(
                c.where(this.getCriteriaBuilder()
                        .and(
                                this.getCriteriaBuilder().equal(p.get("idUser"), user.getId().toString()),
                                this.getCriteriaBuilder().equal(p.get("domainModelClassName"), domainModelClassName),
                                this.getCriteriaBuilder().equal(p.get("attributeName"), attributeName)
                        )
                )
        ).getSingleResult();
    }
}
