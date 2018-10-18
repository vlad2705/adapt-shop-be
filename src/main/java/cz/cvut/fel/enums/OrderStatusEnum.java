package cz.cvut.fel.enums;

public enum OrderStatusEnum {
    PRE_ORDER(1),
    ACCEPTED(2),
    SHIPPED(3),
    DONE(4),
    CANCELED(5);
    
    private final long id;
    
    OrderStatusEnum(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }
}
