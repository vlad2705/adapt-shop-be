package cz.cvut.fel.repository;

import cz.cvut.fel.asf.adapt.gomawe.IUser;
import cz.cvut.fel.asf.adapt.gomawe.storage.IUserModelAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserModelAttribute;
import cz.cvut.fel.asf.persistence.IPersistable;
import cz.cvut.fel.web.client.filter.BaseClientFilter;

import java.util.List;

public interface AdaptUserModelRepository extends IUserModelAttributeDAO<TableUserModelAttribute, Long, Long> {
    
    List<String> findByDomainObjectNameAndName(IUser user, String domainModelClassName, String attributeName);
    
    List<String> findByDomainObjectNameAndName(IUser user, String domainModelClassName, String attributeName, BaseClientFilter filter);
    
    long findRowCount(IUser user, String domainModelClassNam, String attributeName);
}
