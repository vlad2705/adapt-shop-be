package cz.cvut.fel.web.client.filter;

import cz.cvut.fel.asf.tools.StringHelper;
import cz.cvut.fel.web.filter.BaseFilter;
import org.apache.tomcat.util.buf.StringUtils;
import org.primefaces.model.SortOrder;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class ClientProductFilter extends BaseFilter {
    private Long categoryId;
    private List<Long> categoryChildren;
    private List<Long> withoutProducts;
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public List<Long> getCategoryChildren() {
        return categoryChildren;
    }
    
    public void setCategoryChildren(List<Long> categoryChildren) {
        this.categoryChildren = categoryChildren;
    }
    
    public List<Long> getWithoutProducts() {
        return withoutProducts;
    }
    
    public void setWithoutProducts(List<Long> withoutProducts) {
        this.withoutProducts = withoutProducts;
    }
    
    public String getRowCountQuery() {
        return "SELECT count(p) FROM Product p" + getWhere();
    }
    
    public String getQueryString() {
        return "SELECT p.id, p.name, p.price FROM Product p" + getWhere();
    }
    
    public void setParameters(Query query) {
        if ((this.categoryChildren == null || this.categoryChildren.isEmpty()) && this.categoryId != null) {
            query.setParameter("categoryId", this.categoryId);
        }
    }
    
    private String getWhere() {
        StringBuilder sb = new StringBuilder();
        boolean and = false;
        if (this.categoryId != null) {
            sb.append(" WHERE");
            if (categoryChildren != null && !categoryChildren.isEmpty()) {
                sb.append(" p.category.id in (")
                        .append(StringUtils.join(categoryChildren.stream().map(Object::toString).collect(Collectors.toList()), ','))
                        .append(")");
            } else {
                sb.append(" p.category.id = :categoryId");
            }
            and = true;
        }
        if (this.withoutProducts != null && !this.withoutProducts.isEmpty()) {
            if (!and) {
                sb.append(" WHERE");
            } else {
                sb.append(" AND");
            }
            sb.append(" p.id not in (")
                    .append(StringUtils.join(withoutProducts.stream().map(Object::toString).collect(Collectors.toList()), ','))
                    .append(")");
            and = true;
        }
        if (!StringHelper.isNullOrEmpty(getSortField())) {
            sb.append(" ORDER BY p.");
            sb.append(getSortField());
            sb.append(getSortOrder().equals(SortOrder.ASCENDING) ? " ASC" : " DESC");
        }
        return sb.toString();
    }
}
