package cz.cvut.fel.web.filter;

import cz.cvut.fel.asf.tools.StringHelper;
import org.primefaces.model.SortOrder;

import javax.persistence.Query;
import java.math.BigDecimal;

public class ProductFilter extends BaseFilter {
    private String name;
    private String categoryName;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Long categoryId;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public BigDecimal getPriceFrom() {
        return priceFrom;
    }
    
    public void setPriceFrom(BigDecimal priceFrom) {
        this.priceFrom = priceFrom;
    }
    
    public BigDecimal getPriceTo() {
        return priceTo;
    }
    
    public void setPriceTo(BigDecimal priceTo) {
        this.priceTo = priceTo;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getRowCountQuery() {
        return "SELECT count(p) FROM Product p" + getWhere();
    }
    
    public String getQueryString() {
        return "SELECT p.id, p.name, p.price, p.category.name FROM Product p" + getWhere() + getOrder();
    }
    
    public void setParameters(Query query) {
        if (!StringHelper.isNullOrEmpty(this.name)) {
            query.setParameter("productName", "%" + this.name + "%");
        }
        if (!StringHelper.isNullOrEmpty(this.categoryName)) {
            query.setParameter("categoryName", "%" + this.categoryName + "%");
        }
        if (this.priceFrom != null) {
            query.setParameter("priceFrom", this.priceFrom);
        }
        if (this.priceTo != null) {
            query.setParameter("priceTo", this.priceTo);
        }
        if (this.categoryId != null) {
            query.setParameter("categoryId", this.categoryId);
        }
    }
    
    private String getWhere() {
        StringBuilder sb = new StringBuilder();
        boolean and = false;
        if (!StringHelper.isNullOrEmpty(this.name)) {
            sb.append(" WHERE");
            sb.append(" p.name LIKE :productName");
            and = true;
        }
        if (!StringHelper.isNullOrEmpty(this.categoryName)) {
            if (and) {
                sb.append(" AND");
            } else sb.append(" WHERE");
            sb.append(" p.category.name LIKE :categoryName");
            and = true;
        }
        if (this.priceFrom != null) {
            if (and) {
                sb.append(" AND");
            } else sb.append(" WHERE");
            sb.append(" p.price >= :priceFrom");
            and = true;
        }
        if (this.priceTo != null) {
            if (and) {
                sb.append(" AND");
            } else sb.append(" WHERE");
            sb.append(" p.price <= :priceTo");
            and = true;
        }
        if (this.categoryId != null) {
            if (and) {
                sb.append(" AND");
            } else sb.append(" WHERE");
            sb.append(" p.category.id = :categoryId");
            and = true;
        }
        return sb.toString();
    }
    
    private String getOrder() {
        StringBuilder sb = new StringBuilder();
        if (!StringHelper.isNullOrEmpty(getSortField())) {
            sb.append(" ORDER BY p.");
            sb.append(getSortField());
            sb.append(getSortOrder().equals(SortOrder.ASCENDING) ? " ASC" : " DESC");
        }
        return sb.toString();
    }
}
