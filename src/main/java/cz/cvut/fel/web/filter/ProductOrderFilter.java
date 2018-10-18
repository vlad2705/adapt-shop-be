package cz.cvut.fel.web.filter;

import cz.cvut.fel.asf.tools.StringHelper;
import org.primefaces.model.SortOrder;

import javax.persistence.Query;
import java.util.List;

public class ProductOrderFilter extends BaseFilter {
    private String sessionId;
    private String personEmail;
    private List<Long> orderStatuses;
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getPersonEmail() {
        return personEmail;
    }
    
    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }
    
    public List<Long> getOrderStatuses() {
        return orderStatuses;
    }
    
    public void setOrderStatuses(List<Long> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }
    
    public String getRowCountQuery() {
        return "SELECT count(po) FROM ProductOrder po" + getWhere();
    }
    
    public String getQueryString() {
        return "SELECT po.id, po.sessionId, po.person.email, po.orderStatus.name FROM ProductOrder po" + getWhere() + getOrder();
    }
    
    public void setParameters(Query query) {
        if (!StringHelper.isNullOrEmpty(this.sessionId)) {
            query.setParameter("sessionId", "%" + this.sessionId + "%");
        }
        if (!StringHelper.isNullOrEmpty(this.personEmail)) {
            query.setParameter("personEmail", "%" + this.personEmail + "%");
        }
        if (orderStatuses != null && orderStatuses.size() > 0) {
            query.setParameter("orderStatutes", orderStatuses);
        }
    }
    
    private String getWhere() {
        StringBuilder sb = new StringBuilder();
        boolean and = false;
        if (!StringHelper.isNullOrEmpty(this.sessionId)) {
            sb.append(" WHERE");
            sb.append(" po.sessionId LIKE :sessionId");
            and = true;
        }
        if (!StringHelper.isNullOrEmpty(this.personEmail)) {
            if (and) {
                sb.append(" AND");
            } else {
                sb.append(" WHERE");
            }
            sb.append(" po.person.email LIKE :personEmail");
            and = true;
        }
        if (this.orderStatuses != null && this.orderStatuses.size() > 0) {
            if (and) {
                sb.append(" AND");
            } else {
                sb.append(" WHERE");
            }
            sb.append(" po.orderStatus.id IN (:orderStatutes)");
            and = true;
        }
        return sb.toString();
    }
    
    private String getOrder() {
        StringBuilder sb = new StringBuilder();
        if (!StringHelper.isNullOrEmpty(getSortField())) {
            sb.append(" ORDER BY po.");
            if (getSortField().equals("personEmail")) {
                sb.append("person.email");
            } else {
                sb.append(getSortField());
            }
            sb.append(getSortOrder().equals(SortOrder.ASCENDING) ? " ASC" : " DESC");
        }
        return sb.toString();
    }
}
