package cz.cvut.fel.web.dto;

import org.springframework.context.annotation.Scope;

@Scope(value = "session")
public class PictureDto extends IdentificationDto<Long> {
    private String name;
    private byte[] content;
    private String type;
    private long size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
