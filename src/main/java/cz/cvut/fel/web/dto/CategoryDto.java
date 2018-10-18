package cz.cvut.fel.web.dto;

import org.primefaces.model.TreeNode;

public class CategoryDto extends NamedDto<Long> {
    private TreeNode parent;
    
    public TreeNode getParent() {
        return parent;
    }
    
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
    
    public CategoryDto getParentData() {
        return parent == null ? null : (CategoryDto) parent.getData();
    }
}
