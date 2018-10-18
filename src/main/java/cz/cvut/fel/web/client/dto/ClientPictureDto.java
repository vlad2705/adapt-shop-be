package cz.cvut.fel.web.client.dto;

public class ClientPictureDto {
    private byte[] content;
    private String type;
    
    public byte[] getContent() {
        return content;
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
