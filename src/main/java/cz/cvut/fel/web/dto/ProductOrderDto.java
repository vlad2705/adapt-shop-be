package cz.cvut.fel.web.dto;

public class ProductOrderDto extends BaseProductOrderDto {
    private String sessionId;
    private BasePersonDto person;
    private BaseAccountDto account;
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public BasePersonDto getPerson() {
        return person;
    }
    
    public void setPerson(BasePersonDto person) {
        this.person = person;
    }
    
    public BaseAccountDto getAccount() {
        return account;
    }
    
    public void setAccount(BaseAccountDto account) {
        this.account = account;
    }
}
