package cz.cvut.fel.web.client;

import cz.cvut.fel.service.CategoryService;
import cz.cvut.fel.web.client.dto.CategoryTreeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @RequestMapping(value = "/api/categories")
    public List<CategoryTreeDto> categories() {
        return categoryService.getTreeDto().getChildren();
    }
}
