package cz.cvut.fel.integration;

import cz.cvut.fel.model.Category;
import cz.cvut.fel.model.Product;
import cz.cvut.fel.repository.CategoryRepository;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.repository.impl.CategoryRepositoryImpl;
import cz.cvut.fel.repository.impl.ProductRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application.properties")
public class ProductTests {
    
    @TestConfiguration
    static class ProductTestContextConfiguration {
        
        @Bean
        public CategoryRepository categoryRepository() {
            return new CategoryRepositoryImpl(productRepository());
        }
        
        @Bean
        public ProductRepository productRepository() {
            return new ProductRepositoryImpl();
        }
    }
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    private Product product;
    private Category category;
    
    @Before
    public void setUp() {
        category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);
        
        product = new Product();
        product.setName("Product1");
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(1000));
        productRepository.save(product);
    }
    
    @Test
    public void findAll() {
        List<Product> productList = productRepository.findAll();
        Assert.assertNotNull(productList);
        Assert.assertFalse(productList.isEmpty());
    }
    
    @Test
    public void findById() {
        Product product = productRepository.findById(this.product.getId());
        Assert.assertNotNull(product);
    }
    
    @Test
    public void createProduct() {
        Product product = create("Product 2");
        Assert.assertNotNull(productRepository.findById(product.getId()));
    }
    
    @Test
    public void updateProduct() {
        String name = "Product3";
        product.setName(name);
        productRepository.save(product);
        Assert.assertEquals(productRepository.findById(product.getId()).getName(), name);
    }
    
    @Test
    public void deleteProduct() {
        Product product = create("Product 3");
        productRepository.delete(product);
        Product deletedProduct = productRepository.findById(product.getId());
        Assert.assertNull(deletedProduct);
    }
    
    private Product create(String name) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(2000));
        productRepository.save(product);
        return product;
    }
}
