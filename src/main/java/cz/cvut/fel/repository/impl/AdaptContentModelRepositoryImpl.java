package cz.cvut.fel.repository.impl;

import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableContentUnitModelAttribute;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableContentUnitModelAttributeDAOImpl;
import cz.cvut.fel.asf.tools.ArgsChecker;
import cz.cvut.fel.repository.AdaptContentModelRepository;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class AdaptContentModelRepositoryImpl extends TableContentUnitModelAttributeDAOImpl implements AdaptContentModelRepository {
    
    @Override
    public List<String> findByNameAndFilter(String attributeName, BaseClientFilter filter) {
        (new ArgsChecker()).checkRequiredParameters("attributeName", attributeName);
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<String> c = cb.createQuery(String.class);
        Root<TableContentUnitModelAttribute> p = c.from(TableContentUnitModelAttribute.class);
        c.orderBy(cb.desc(p.get("attributeValue").as(Integer.TYPE)));
        c.multiselect(p.get("domainModelInstanceId"));
        return this.getEntityManager().createQuery(
                c.where(this.getCriteriaBuilder().and(
                        this.getCriteriaBuilder().equal(p.get("attributeName"), attributeName))
                )
        ).setFirstResult(filter.getFirst()).setMaxResults(filter.getPageSize()).getResultList();
    }
    
    @Override
    public long findRowCount(String attributeName) {
        (new ArgsChecker()).checkRequiredParameters("attributeName", attributeName);
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Long> c = cb.createQuery(Long.class);
        Root<TableContentUnitModelAttribute> p = c.from(TableContentUnitModelAttribute.class);
        c.select(cb.count(p));
        return this.getEntityManager().createQuery(
                c.where(this.getCriteriaBuilder().and(
                        this.getCriteriaBuilder().equal(p.get("attributeName"), attributeName))
                )
        ).getSingleResult();
    }
}
