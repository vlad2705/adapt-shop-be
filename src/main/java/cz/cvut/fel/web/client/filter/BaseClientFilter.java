package cz.cvut.fel.web.client.filter;

public class BaseClientFilter {
    private int first = 0;
    private int pageSize = 10;
    
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
}
