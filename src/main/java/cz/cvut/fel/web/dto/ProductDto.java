package cz.cvut.fel.web.dto;

import org.primefaces.model.TreeNode;

import java.math.BigDecimal;

public class ProductDto extends BaseProductDto {
    private BigDecimal price;
    private String description;
    private TreeNode category;
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public TreeNode getCategory() {
        if (category != null) {
            this.category.setSelected(true);
            this.category.setExpanded(true);
        }
        return category;
    }
    
    public void setCategory(TreeNode category) {
        this.category = category;
    }
    
    public CategoryDto getCategoryData() {
        return category == null ? null : (CategoryDto)category.getData();
    }
}
