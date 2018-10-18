package cz.cvut.fel.unit;

import cz.cvut.fel.model.*;
import cz.cvut.fel.repository.CategoryRepository;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.service.PersonService;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.client.dto.ClientProductDetailDto;
import cz.cvut.fel.web.client.dto.ClientProductDto;
import cz.cvut.fel.web.client.dto.ClientProductFilterDto;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import cz.cvut.fel.web.client.filter.ClientProductFilter;
import cz.cvut.fel.web.data.ProductData;
import cz.cvut.fel.web.dto.BaseProductDto;
import cz.cvut.fel.web.dto.CategoryDto;
import cz.cvut.fel.web.dto.ProductDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTests {

    @Autowired
    private ProductService productService;
    
    @MockBean
    private PersonService personService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    private Product product;
    private Category category;
    private ProductDto productDto1;
    private ProductDto productDto2;
    private TreeNode categoryTree;
    
    @Before
    public void setUp() {
        product = ProductTest.defaultProduct();
        category = CategoryTest.defaultCategory();
        Person person = PersonTest.defaultPerson();
    
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setParent(null);
        
        categoryTree = new DefaultTreeNode(categoryDto, null);
        
        productDto1 = new ProductDto();
        productDto1.setId(1L);
        productDto1.setName("Soccer ball");
        productDto1.setCategory(categoryTree);
        productDto1.setPrice(BigDecimal.valueOf(300));

        productDto2 = new ProductDto();
        productDto2.setName("Soccer ball");
        productDto2.setCategory(categoryTree);
        productDto2.setPrice(BigDecimal.valueOf(300));
        

        Mockito.when(productRepository.findById(product.getId())).thenReturn(product);
        Mockito.when(productRepository.findByName(product.getName())).thenReturn(Collections.singletonList(product));
        Mockito.when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        Mockito.when(categoryRepository.findById(16L)).thenReturn(category);
        Mockito.when(personService.getAuthorizationPerson()).thenReturn(person);
    }
    
    @Test
    public void convertToBaseDto() {
        BaseProductDto productDto = productService.convertToBaseDto(product);
        Assert.assertEquals(productDto.getId(), product.getId());
        Assert.assertEquals(productDto.getName(), product.getName());
    }

    @Test
    public void convertToDto() {
        ProductDto productDto = productService.convertToDto(product);
        Assert.assertEquals(productDto.getId(), product.getId());
        Assert.assertEquals(productDto.getName(), product.getName());
        Assert.assertEquals(productDto.getPrice(), product.getPrice());
    }
    
    @Test
    public void convertClientToDto() {
        ClientProductDto productDto = productService.convertToClientDto(product);
        Assert.assertEquals(productDto.getId(), product.getId());
        Assert.assertEquals(productDto.getName(), product.getName());
        Assert.assertEquals(productDto.getPrice(), product.getPrice());
    }

    @Test
    public void convertToModelWithId() {
        Product product1 = productService.convertToModel(productDto1);
        Assert.assertEquals(product1.getId(), productDto1.getId());
        Assert.assertEquals(product1.getName(), productDto1.getName());
        Assert.assertEquals(product1.getPrice(), productDto1.getPrice());
        Assert.assertEquals(product1.getCategory(), category);
    }

    @Test
    public void convertToModelWithoutId() {
        Product product1 = productService.convertToModel(productDto2);
        Assert.assertEquals(product1.getName(), productDto2.getName());
        Assert.assertEquals(product1.getPrice(), productDto2.getPrice());
        Assert.assertEquals(product1.getCategory(), category);
    }
    
    @Test
    public void getById() {
        ProductDto productDto = productService.getById(product.getId());
        Assert.assertEquals(productDto.getId(), product.getId());
        Assert.assertEquals(productDto.getName(), product.getName());
        Assert.assertEquals(productDto.getPrice(), product.getPrice());
        Assert.assertEquals(productDto.getCategory(), categoryTree);
    }
    
    @Test
    public void getDataById() {
        ProductData productData = productService.getDataById(product.getId());
        Assert.assertEquals(productData.getId(), product.getId());
        Assert.assertEquals(productData.getName(), product.getName());
        Assert.assertEquals(productData.getPrice(), product.getPrice());
        Assert.assertEquals(productData.getCategory(), category.getName());
    }
    
    @Test
    public void getDataByStringId() {
        ProductData productData = productService.getDataById(product.getId().toString());
        Assert.assertEquals(productData.getId(), product.getId());
        Assert.assertEquals(productData.getName(), product.getName());
        Assert.assertEquals(productData.getPrice(), product.getPrice());
        Assert.assertEquals(productData.getCategory(), category.getName());
    }
    
    @Test
    public void getByName() {
        List<BaseProductDto> productDtoList = productService.getByName(product.getName());
        Assert.assertEquals(productDtoList.size(), 1);
        Assert.assertEquals(productDtoList.get(0).getId(), product.getId());
        Assert.assertEquals(productDtoList.get(0).getName(), product.getName());
    }
    
    @Test
    public void getProductDetail() {
        ClientProductDetailDto productDetailDto = productService.getProductDetail(product.getId());
        Assert.assertEquals(productDetailDto.getId(), product.getId());
        Assert.assertEquals(productDetailDto.getName(), product.getName());
        Assert.assertEquals(productDetailDto.getPrice(), product.getPrice());
        Assert.assertEquals(productDetailDto.getDescription(), product.getDescription());
    }
}
