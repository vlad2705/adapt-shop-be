package cz.cvut.fel.web.data;

public abstract class BaseData {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRowKey() {
        return id.toString();
    }
}
