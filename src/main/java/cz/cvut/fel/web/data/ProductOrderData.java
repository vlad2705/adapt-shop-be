package cz.cvut.fel.web.data;

public class ProductOrderData extends BaseData {
    private String sessionId;
    private String personEmail;
    private String orderStatus;
    
    public ProductOrderData() {
    }
    
    public ProductOrderData(Object[] data) {
        this.sessionId = (String) data[1];
        this.personEmail = (String) data[2];
        this.orderStatus = (String) data[3];
        setId((Long) data[0]);
    }
    
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
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
