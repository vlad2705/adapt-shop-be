package cz.cvut.fel.service;

import cz.cvut.fel.model.Category;
import cz.cvut.fel.web.client.dto.CategoryTreeDto;
import cz.cvut.fel.web.dto.CategoryDto;
import cz.cvut.fel.web.filter.CategoryFilter;
import org.primefaces.model.TreeNode;

import java.util.List;

public interface CategoryService {

    CategoryDto convertToDto(Category category);
    
    CategoryTreeDto convertToTreeDto(Category category);

    TreeNode convertToTreeNode(Category category);

    TreeNode convertToTreeNode(CategoryDto categoryDto);

    TreeNode getTree();
    
    CategoryTreeDto getTreeDto();

    TreeNode getTree(Long selectedId);

    Category convertToModel(CategoryDto categoryDto);

    Category convertToModel(TreeNode node);

    Category convertToModel(Category category, CategoryDto categoryDto);

    List<CategoryDto> getAll();

    List<CategoryDto> getAll(Long id);

    List<CategoryDto> getAll(String id);

    CategoryDto getById(Long id);

    CategoryDto getById(String id);

    TreeNode getByFilter(CategoryFilter filter);
    
    List<Long> getCategoryWithChildren(Long categoryId);

    CategoryDto save(CategoryDto categoryDto);

    void delete(long id);
}
