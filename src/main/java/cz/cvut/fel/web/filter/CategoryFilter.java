package cz.cvut.fel.web.filter;

import cz.cvut.fel.asf.tools.StringHelper;
import org.primefaces.model.SortOrder;

import javax.persistence.Query;

public class CategoryFilter extends BaseFilter {
    private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRowCountQuery() {
        return "SELECT count(c) FROM Category c" + getWhere();
    }
    
    public String getQueryString() {
        return "SELECT c FROM Category c" + getWhere() + getOrder();
    }
    
    public void setParameters(Query query) {
        if (!StringHelper.isNullOrEmpty(this.name)) {
            query.setParameter("name", "%" + this.name + "%");
        }
    }
    
    private String getWhere() {
        StringBuilder sb = new StringBuilder();
        boolean and = false;
        if (!StringHelper.isNullOrEmpty(this.name)) {
            sb.append(" WHERE");
            sb.append(" c.name LIKE :name");
            and = true;
        }
        return sb.toString();
    }
    
    private String getOrder() {
        StringBuilder sb = new StringBuilder();
        if (!StringHelper.isNullOrEmpty(getSortField())) {
            sb.append(" ORDER BY c.");
            sb.append(getSortField());
            sb.append(getSortOrder().equals(SortOrder.ASCENDING) ? " ASC" : " DESC");
        }
        return sb.toString();
    }
}
