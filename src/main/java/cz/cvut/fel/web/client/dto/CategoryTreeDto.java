package cz.cvut.fel.web.client.dto;

import cz.cvut.fel.web.dto.NamedDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryTreeDto extends NamedDto<Long> {
    private List<CategoryTreeDto> children;
    
    public CategoryTreeDto() {
        this.children = new ArrayList<>();
    }
    
    public List<CategoryTreeDto> getChildren() {
        return children;
    }
    
    public void setChildren(List<CategoryTreeDto> children) {
        this.children = children;
    }
}
