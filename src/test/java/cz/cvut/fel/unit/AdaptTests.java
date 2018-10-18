package cz.cvut.fel.unit;

import cz.cvut.fel.model.*;
import cz.cvut.fel.repository.AdaptContentModelRepository;
import cz.cvut.fel.repository.AdaptUserModelRepository;
import cz.cvut.fel.repository.CategoryRepository;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.service.PersonService;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.client.dto.ClientProductDto;
import cz.cvut.fel.web.client.dto.ClientProductFilterDto;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import cz.cvut.fel.web.client.filter.ClientProductFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static cz.cvut.fel.enums.AttributeName.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdaptTests {
    
    @Autowired
    private ProductService productService;
    
    @MockBean
    private PersonService personService;
    
    @MockBean
    private ProductRepository productRepository;
    
    @MockBean
    private CategoryRepository categoryRepository;
    
    @MockBean
    private AdaptUserModelRepository adaptUserModelRepository;
    
    @MockBean
    private AdaptContentModelRepository adaptContentModelRepository;
    
    private BaseClientFilter baseFilter;
    private ClientProductFilter filter;
    private List<String> ids;
    private List<Product> products;
    private List<ClientProductDto> productDtos;
    
    @Before
    public void setUp() {
        Category category = CategoryTest.defaultCategory();
        Person person = PersonTest.defaultPerson();
        
        baseFilter = new BaseClientFilter();
        filter = new ClientProductFilter();
    
        ids = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        products = ProductTest.defaultProductList();
    
        productDtos = products.stream().filter(product -> product.getId() <= ids.size()).map(product -> {
            ClientProductDto dto = new ClientProductDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            return dto;
        }).collect(Collectors.toList());
    
    
        products.forEach(product -> Mockito.when(productRepository.findById(product.getId())).thenReturn(product));
        Mockito.when(productRepository.findByClientFilter(filter)).thenReturn(productDtos);
        productDtos.forEach(productDto -> Mockito.when(productRepository.findPictureIdByProduct(productDto.getId())).thenReturn(null));
        Mockito.when(personService.getAuthorizationPerson()).thenReturn(person);
        Mockito.when(categoryRepository.findChildren(category.getId())).thenReturn(Collections.emptyList());
        Mockito.when(adaptUserModelRepository.findByDomainObjectNameAndName(person, Product.class.getName(), PRODUCT_VISIT_TIME.getName(), baseFilter)).thenReturn(ids);
        Mockito.when(adaptUserModelRepository.findByDomainObjectNameAndName(person, Product.class.getName(), PURCHASE_TIME.getName())).thenReturn(ids);
        Mockito.when(adaptContentModelRepository.findByNameAndFilter(PURCHASE_COUNT.getName(), baseFilter)).thenReturn(ids);
        Mockito.when(adaptUserModelRepository.findRowCount(person, Product.class.getName(), PRODUCT_VISIT_TIME.getName())).thenReturn(Long.valueOf(products.size()));
        Mockito.when(productRepository.findRowCountByClientFilter(filter)).thenReturn(Long.valueOf(products.size()));
        Mockito.when(adaptContentModelRepository.findRowCount(PURCHASE_COUNT.getName())).thenReturn(Long.valueOf(products.size()));
    }
    
    @Test
    public void getViewedByFilterAmount() {
        ClientProductFilterDto productFilterDto = productService.getViewedByFilter(baseFilter);
        Assert.assertEquals(productFilterDto.getProducts().size(), ids.size());
        Assert.assertEquals(productFilterDto.getTotalProducts(), products.size());
    }
    
    @Test
    public void getViewedByFilterContains() {
        ClientProductFilterDto productFilterDto = productService.getViewedByFilter(baseFilter);
        productFilterDto.getProducts().forEach(productDto -> products.forEach(product -> {
            if (product.getId().equals(productDto.getId())) {
                Assert.assertEquals(productDto.getName(), product.getName());
                Assert.assertEquals(productDto.getPrice(), product.getPrice());
            }
        }));
    }
    
    @Test
    public void getRecommendedByFilterAmount() {
        ClientProductFilterDto productFilterDto = productService.getRecommendedByFilter(filter);
        Assert.assertEquals(productFilterDto.getProducts().size(), productDtos.size());
        Assert.assertEquals(productFilterDto.getTotalProducts(), products.size());
    }
    
    @Test
    public void getRecommendedByFilterContains() {
        ClientProductFilterDto productFilterDto = productService.getRecommendedByFilter(filter);
        productFilterDto.getProducts().forEach(productDto -> products.forEach(product -> {
            if (product.getId().equals(productDto.getId())) {
                Assert.assertEquals(productDto.getName(), product.getName());
                Assert.assertEquals(productDto.getPrice(), product.getPrice());
            }
        }));
    }
    
    @Test
    public void getBestSellersByFilterAmount() {
        ClientProductFilterDto productFilterDto = productService.getBestSellersByFilter(baseFilter);
        Assert.assertEquals(productFilterDto.getProducts().size(), ids.size());
        Assert.assertEquals(productFilterDto.getTotalProducts(), products.size());
    }
    
    @Test
    public void getBestSellersByFilterContains() {
        ClientProductFilterDto productFilterDto = productService.getBestSellersByFilter(baseFilter);
        productFilterDto.getProducts().forEach(productDto -> products.forEach(product -> {
            if (product.getId().equals(productDto.getId())) {
                Assert.assertEquals(productDto.getName(), product.getName());
                Assert.assertEquals(productDto.getPrice(), product.getPrice());
            }
        }));
    }
}
