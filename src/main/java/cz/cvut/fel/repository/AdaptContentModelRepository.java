package cz.cvut.fel.repository;

import cz.cvut.fel.asf.adapt.gomawe.storage.IContentUnitModelAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableContentUnitModelAttribute;
import cz.cvut.fel.web.client.filter.BaseClientFilter;

import java.util.List;

public interface AdaptContentModelRepository extends IContentUnitModelAttributeDAO<TableContentUnitModelAttribute, Long> {
    
    List<String> findByNameAndFilter(String attributeName, BaseClientFilter filter);

    long findRowCount(String attributeName);
}
