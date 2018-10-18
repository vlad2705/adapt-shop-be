package cz.cvut.fel.web.converter;

import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.web.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@Service
@FacesConverter(forClass = CategoryDto.class)
public class CategoryConverter implements Converter {

    private final CategoryService categoryService;

    @Autowired
    public CategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (!StringUtils.isEmpty(value)) {
            try {
                return categoryService.getById(Long.valueOf(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid category."));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        return object != null ? ((CategoryDto)object).getId().toString() : null;
    }
}
