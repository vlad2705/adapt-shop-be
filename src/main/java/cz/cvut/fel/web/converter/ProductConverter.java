package cz.cvut.fel.web.converter;

import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.dto.BaseProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Service
public class ProductConverter implements Converter {
    
    private ProductService productService;
    
    public ProductConverter() {
    }
    
    @Autowired
    public ProductConverter(ProductService productService) {
        this.productService = productService;
    }
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (!StringUtils.isEmpty(value)) {
            try {
                return productService.getById(Long.valueOf(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid product."));
            }
        }
        return null;
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        return object != null ? ((BaseProductDto)object).getId().toString() : null;
    }
}
