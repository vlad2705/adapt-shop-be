package cz.cvut.fel.enums;

public enum AttributeName {
    PRODUCT_VISIT_TIME("product_visit_time"),
    PURCHASE_TIME("purchase_time"),
    PURCHASE_COUNT("purchase_count");
    
    private final String name;
    
    AttributeName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
