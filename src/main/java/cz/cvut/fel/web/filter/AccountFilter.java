package cz.cvut.fel.web.filter;

import cz.cvut.fel.asf.tools.StringHelper;
import org.primefaces.model.SortOrder;

import javax.persistence.Query;

public class AccountFilter extends BaseFilter {
    private String firstName;
    private String lastName;
    private String email;
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRowCountQuery() {
        return "SELECT count(a) FROM Account a" + getWhere();
    }
    
    public String getQueryString() {
        return "SELECT a.id, a.firstName, a.lastName, a.person.email FROM Account a" + getWhere() + getOrder();
    }
    
    public void setParameters(Query query) {
        if (!StringHelper.isNullOrEmpty(this.firstName)) {
            query.setParameter("firstName", "%" + this.firstName + "%");
        }
        if (!StringHelper.isNullOrEmpty(this.lastName)) {
            query.setParameter("lastName", "%" + this.lastName + "%");
        }
        if (!StringHelper.isNullOrEmpty(this.email)) {
            query.setParameter("email", "%" + this.email + "%");
        }
    }
    
    private String getWhere() {
        StringBuilder sb = new StringBuilder();
        boolean and = false;
        if (!StringHelper.isNullOrEmpty(this.firstName)) {
            sb.append(" WHERE");
            sb.append(" a.firstName LIKE :firstName");
            and = true;
        }
        if (!StringHelper.isNullOrEmpty(this.lastName)) {
            if (and) {
                sb.append(" AND");
            } else {
                sb.append(" WHERE");
            }
            sb.append(" a.lastName LIKE :lastName");
            and = true;
        }
        if (!StringHelper.isNullOrEmpty(this.email)) {
            if (and) {
                sb.append(" AND");
            } else {
                sb.append(" WHERE");
            }
            sb.append(" a.person.email LIKE :email");
            and = true;
        }
        return sb.toString();
    }
    
    private String getOrder() {
        StringBuilder sb = new StringBuilder();
        if (!StringHelper.isNullOrEmpty(getSortField())) {
            sb.append(" ORDER BY a.");
            if (getSortField().equals("email")) {
                sb.append("person.email");
            } else {
                sb.append(getSortField());
            }
            sb.append(getSortOrder().equals(SortOrder.ASCENDING) ? " ASC" : " DESC");
        }
        return sb.toString();
    }
}
