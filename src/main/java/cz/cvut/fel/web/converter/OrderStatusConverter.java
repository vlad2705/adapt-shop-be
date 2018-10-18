package cz.cvut.fel.web.converter;

import cz.cvut.fel.service.OrderStatusService;
import cz.cvut.fel.web.dto.OrderStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@Service
@FacesConverter("orderStatusConverter")
public class OrderStatusConverter implements Converter {
    
    @Autowired
    private OrderStatusService orderStatusService;
    
//    @Autowired
//    public OrderStatusConverter(OrderStatusService orderStatusService) {
//        this.orderStatusService = orderStatusService;
//    }
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                return orderStatusService.getById(value);
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid status."));
            }
        }
        else {
            return null;
        }
    }
    
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((OrderStatusDto) object).getId());
        }
        else {
            return null;
        }
    }
}
