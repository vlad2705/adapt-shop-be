package cz.cvut.fel.model;

import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableContentUnitModel;
import cz.cvut.fel.asf.persistence.IPersistable;
import static cz.cvut.fel.enums.AttributeName.PURCHASE_COUNT;

public class AdaptContentModel extends TableContentUnitModel {
    
    public AdaptContentModel(IPersistable domainModelInstance) {
        super(domainModelInstance);
    }
    
    public void incrementPurchaseCount(int quantity) {
        String count = getPurchaseCount();
        if (count == null || count.isEmpty()) {
            setPurchaseCount(quantity);
        } else {
            setPurchaseCount(Integer.valueOf(count) + quantity);
        }
    }
    
    private String getPurchaseCount() {
        return getAttributeValue(PURCHASE_COUNT.getName());
    }
    
    private void setPurchaseCount(int count) {
        putAttributeValue(PURCHASE_COUNT.getName(), String.valueOf(count));
    }
}
