package cz.cvut.fel.web.filter;

import org.primefaces.model.SortOrder;

public abstract class BaseFilter {
    private int first = 0;
    private int pageSize = 10;
    private String sortField;
    private SortOrder sortOrder;
    
    public abstract String getRowCountQuery();
    public abstract String getQueryString();
    
    public int getFirst() {
        return first;
    }
    
    public void setFirst(int first) {
        this.first = first;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getSortField() {
        return sortField;
    }
    
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
    
    public SortOrder getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
