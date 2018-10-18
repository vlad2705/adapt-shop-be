package cz.cvut.fel.model;

import cz.cvut.fel.asf.adapt.gomawe.IUser;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserModel;
import cz.cvut.fel.asf.persistence.IPersistable;
import static cz.cvut.fel.enums.AttributeName.*;

import java.time.LocalDateTime;

public class AdaptUserModel extends TableUserModel {
    
    public AdaptUserModel(IUser user) {
        super(user);
    }
    
    public void setProductVisitTime(IPersistable object) {
        putAttributeValue(object, PRODUCT_VISIT_TIME.getName(), LocalDateTime.now().toString());
    }
    
    public void setPurchaseTime(IPersistable object) {
        putAttributeValue(object, PURCHASE_TIME.getName(), LocalDateTime.now().toString());
    }
}
