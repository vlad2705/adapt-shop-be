package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Category;
import cz.cvut.fel.repository.CategoryRepository;
import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.web.client.dto.CategoryTreeDto;
import cz.cvut.fel.web.dto.CategoryDto;
import cz.cvut.fel.web.filter.CategoryFilter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto convertToDto(Category category) {
        if (category != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setParent(convertToTreeNode(convertToDto(category.getParent())));
            return categoryDto;
        }
        return null;
    }
    
    @Override
    public CategoryTreeDto convertToTreeDto(Category category) {
        if (category != null) {
            CategoryTreeDto categoryTreeDto = new CategoryTreeDto();
            categoryTreeDto.setId(category.getId());
            categoryTreeDto.setName(category.getName());
            return categoryTreeDto;
        }
        return null;
    }

    @Override
    public TreeNode convertToTreeNode(Category category) {
        return convertToTreeNode(convertToDto(category));
    }

    @Override
    public TreeNode convertToTreeNode(CategoryDto categoryDto) {
        if (categoryDto == null) return null;
        return new DefaultTreeNode(categoryDto, convertToTreeNode(categoryDto.getParentData()));
    }

    @Override
    public Category convertToModel(CategoryDto categoryDto) {
        if (categoryDto != null) {
            Category category = categoryRepository.findById(categoryDto.getId());
            return convertToModel(category, categoryDto);
        }
        return null;
    }

    @Override
    public Category convertToModel(TreeNode node) {
        return convertToModel((CategoryDto) node.getData());
    }

    @Override
    public Category convertToModel(Category category, CategoryDto categoryDto) {
        if (category != null && categoryDto != null) {
            category.setName(categoryDto.getName());
            category.setParent(categoryDto.getParent() != null
                    ? categoryRepository.findById(categoryDto.getParentData().getId())
                    : null
            );
            return category;
        }
        return null;
    }

    @Override
    public TreeNode getTree() {
        TreeNode root = new DefaultTreeNode();
        List<Category> categories = categoryRepository.findRoots();
        categories.forEach(category -> createTreeNode(category, root));
        return root;
    }
    
    @Override
    public CategoryTreeDto getTreeDto() {
        CategoryTreeDto root = new CategoryTreeDto();
        List<Category> categories = categoryRepository.findRoots();
        categories.forEach(category -> createTreeDto(category, root));
        return root;
    }

    @Override
    public TreeNode getTree(Long selectedId) {
        if (selectedId == null) return getTree();
        TreeNode root = new DefaultTreeNode();
        List<Category> categories = categoryRepository.findRoots();
        categories.forEach(category -> createTreeNode(category, root, selectedId));
        return root;
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getAll(Long id) {
        if (id == null) {
            return getAll();
        }
        return categoryRepository.findAll(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getAll(String id) {
        return id == null || id.isEmpty() ? getAll() : getAll(Long.valueOf(id));
    }

    @Override
    public CategoryDto getById(Long id) {
        return convertToDto(categoryRepository.findById(id));
    }

    @Override
    public CategoryDto getById(String id) {
        return id == null || id.isEmpty() ? new CategoryDto() : getById(Long.valueOf(id));
    }

    @Override
    public TreeNode getByFilter(CategoryFilter filter) {
        TreeNode root = new DefaultTreeNode();
        List<Category> categories = categoryRepository.findByFilter(filter);
        categories.forEach(category -> createTreeNode(category, root));
        return root;
    }
    
    @Override
    public List<Long> getCategoryWithChildren(Long categoryId) {
        if (categoryId != null) {
            List<Long> categories = new ArrayList<>();
            categories.add(categoryId);
            List<Long> children = categoryRepository.findChildren(categoryId);
            children.forEach(id -> categories.addAll(getCategoryWithChildren(id)));
            return categories;
        }
        return Collections.emptyList();
    }
    
    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        return categoryDto.getId() == null ? create(categoryDto) : update(categoryDto);
    }

    private CategoryDto create(CategoryDto categoryDto) {
        if (categoryDto != null) {
            Category category = convertToModel(new Category(), categoryDto);
            categoryRepository.save(category);
            return convertToDto(category);
        }
        return null;
    }

    private CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto != null) {
            Category category = convertToModel(categoryDto);
            categoryRepository.update(category);
            return convertToDto(category);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        categoryRepository.delete(categoryRepository.findById(id));
    }

    private void createTreeNode(Category category, TreeNode parent) {
        CategoryDto categoryDto = convertToDto(category);
        TreeNode root = new DefaultTreeNode(categoryDto, parent);
        List<Category> children = categoryRepository.findChildren(category);
        children.forEach(child -> createTreeNode(child, root));
    }
    
    private void createTreeDto(Category category, CategoryTreeDto parent) {
        CategoryTreeDto categoryTreeDto = convertToTreeDto(category);
        List<Category> children = categoryRepository.findChildren(category);
        children.forEach(child -> createTreeDto(child, categoryTreeDto));
        parent.getChildren().add(categoryTreeDto);
    }

    private void createTreeNode(Category category, TreeNode parent, long selectedId) {
        CategoryDto categoryDto = convertToDto(category);
        TreeNode root = new DefaultTreeNode(categoryDto, parent);
        if (category.getId().equals(selectedId)) {
            root.setSelected(true);
            if (root.getParent() != null) {
                expandNode(root.getParent());
            }
        }
        List<Category> children = categoryRepository.findChildren(category);
        children.forEach(child -> createTreeNode(child, root, selectedId));
    }

    private void expandNode(TreeNode node) {
        if (node != null) {
            node.setExpanded(true);
            expandNode(node.getParent());
        }
    }
}
