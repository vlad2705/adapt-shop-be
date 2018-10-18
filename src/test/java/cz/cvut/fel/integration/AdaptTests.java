package cz.cvut.fel.integration;

import cz.cvut.fel.asf.adapt.gomawe.AdaptationManager;
import cz.cvut.fel.asf.adapt.gomawe.storage.IContentUnitModelAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.IUserModelAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.IUserProfileAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableContentUnitModelAttributeDAOImpl;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserModelAttributeDAOImpl;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserProfileAttributeDAOImpl;
import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;
import cz.cvut.fel.model.*;
import cz.cvut.fel.repository.*;
import cz.cvut.fel.repository.impl.*;
import cz.cvut.fel.service.PersonService;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static cz.cvut.fel.enums.AttributeName.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application.properties")
public class AdaptTests {
    
    @TestConfiguration
    static class AdaptTestContextConfiguration {
        
        @Bean
        public CategoryRepository categoryRepository() {
            return new CategoryRepositoryImpl(productRepository());
        }
        
        @Bean
        public ProductRepository productRepository() {
            return new ProductRepositoryImpl();
        }
    
        @Bean
        public AdaptUserModelRepository adaptUserModelRepository() {
            return new AdaptUserModelRepositoryImpl();
        }
    
        @Bean
        public AdaptContentModelRepository adaptContentModelRepository() {
            return new AdaptContentModelRepositoryImpl();
        }
    
        @Bean
        public IUserModelAttributeDAO userModelAttributeDAO() {
            return new TableUserModelAttributeDAOImpl();
        }
    
        @Bean
        public IUserProfileAttributeDAO userProfileAttributeDAO() {
            return new TableUserProfileAttributeDAOImpl();
        }
    
        @Bean
        public IContentUnitModelAttributeDAO contentUnitModelAttributeDAO() {
            return new TableContentUnitModelAttributeDAOImpl();
        }
    
        @Bean
        public AccountRepository accountRepository() {
            return new AccountRepositoryImpl();
        }
    
        @Bean
        public IDAO userDAO() {
            return new PersonRepositoryImpl(accountRepository());
        }
    
        @Bean
        public String userModelImplementationClass() {
            return AdaptUserModel.class.getName();
        }
    
        @Bean
        public String contentUnitModelImplementationClass() {
            return AdaptContentModel.class.getName();
        }
    
        @Bean
        public AdaptationManager adaptationManager() throws InstantiationException {
            AdaptationManager adaptationManager = new AdaptationManager();
            adaptationManager.setUserModelAttributeDAO(userModelAttributeDAO());
            adaptationManager.setUserProfileAttributeDAO(userProfileAttributeDAO());
            adaptationManager.setContentUnitModelAttributeDAO(contentUnitModelAttributeDAO());
            adaptationManager.setUserDAO(userDAO());
            adaptationManager.setUserModelImplementationClass(userModelImplementationClass());
            adaptationManager.setContentUnitModelImplementationClass(contentUnitModelImplementationClass());
            return adaptationManager;
        }
    }
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private AdaptUserModelRepository adaptUserModelRepository;
    
    @Autowired
    private AdaptContentModelRepository adaptContentModelRepository;
    
    @MockBean
    private PersonService personService;
    
    private Category category;
    private List<Product> products;
    private Person person;
    private BaseClientFilter baseFilter;
    
    @Before
    public void setUp() {
        person = PersonTest.defaultPerson();
        category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);
        baseFilter = new BaseClientFilter();
        products = Arrays.asList(
                new Product("Product 1", BigDecimal.valueOf(1000), category, "Description"),
                new Product("Product 2", BigDecimal.valueOf(2000), category, "Description"),
                new Product("Product 3", BigDecimal.valueOf(3000), category, "Description"),
                new Product("Product 4", BigDecimal.valueOf(4000), category, "Description"),
                new Product("Product 5", BigDecimal.valueOf(5000), category, "Description"),
                new Product("Product 6", BigDecimal.valueOf(6000), category, "Description"),
                new Product("Product 7", BigDecimal.valueOf(7000), category, "Description"),
                new Product("Product 8", BigDecimal.valueOf(8000), category, "Description"),
                new Product("Product 9", BigDecimal.valueOf(9000), category, "Description"),
                new Product("Product 10", BigDecimal.valueOf(10000), category, "Description"),
                new Product("Product 11", BigDecimal.valueOf(11000), category, "Description"),
                new Product("Product 12", BigDecimal.valueOf(12000), category, "Description"),
                new Product("Product 13", BigDecimal.valueOf(13000), category, "Description"),
                new Product("Product 14", BigDecimal.valueOf(14000), category, "Description"),
                new Product("Product 15", BigDecimal.valueOf(15000), category, "Description")
        );
        products.forEach(product -> productRepository.save(product));
    
        Mockito.when(personService.getAuthorizationPerson()).thenReturn(person);
    }
    
    @Test
    public void setProductVisit() {
        Product product = products.get(0);
        AdaptUserModel model = AdaptationManager.getCurrent().getUserModel(person);
        model.setProductVisitTime(product);
    }
    
    @Test
    public void findRecentlyViewed() {
        List<Product> recentlyViewedProducts = products.stream().filter(product -> product.getId() % 2 != 0).collect(Collectors.toList());
        AdaptUserModel model = AdaptationManager.getCurrent().getUserModel(person);
        recentlyViewedProducts.forEach(model::setProductVisitTime);
        List<Long> recentlyViewedProductIds = recentlyViewedProducts.stream().map(AbstractPersistable::getId).collect(Collectors.toList());
        
        List<String> recentlyViewedIds = adaptUserModelRepository
                .findByDomainObjectNameAndName(person, Product.class.getName(), PRODUCT_VISIT_TIME.getName(), baseFilter);
        
        Assert.assertTrue(recentlyViewedIds.size() == baseFilter.getPageSize() || recentlyViewedIds.size() == recentlyViewedProductIds.size());
        
        recentlyViewedIds.forEach(id -> Assert.assertTrue(recentlyViewedProductIds.contains(Long.valueOf(id))));
    }
    
    @Test
    public void setPurchase() {
        Product product = products.get(1);
        AdaptUserModel model = AdaptationManager.getCurrent().getUserModel(person);
        model.setPurchaseTime(product);
    }
    
    @Test
    public void findRecommended() {
        List<Product> recommendedProducts = products.stream().filter(product -> product.getId() % 2 == 0).collect(Collectors.toList());
        AdaptUserModel model = AdaptationManager.getCurrent().getUserModel(person);
        recommendedProducts.forEach(model::setPurchaseTime);
        List<Long> recommendedProductIds = recommendedProducts.stream().map(AbstractPersistable::getId).collect(Collectors.toList());
        
        List<String> recommendedIds = adaptUserModelRepository
                .findByDomainObjectNameAndName(person, Product.class.getName(), PURCHASE_TIME.getName());
    
        Assert.assertTrue(recommendedIds.size() == baseFilter.getPageSize() || recommendedIds.size() == recommendedProductIds.size());
    
        recommendedIds.forEach(id -> Assert.assertTrue(recommendedProductIds.contains(Long.valueOf(id))));
    }
    
    @Test
    public void incrementPurchaseCount() {
        Product product = products.get(2);
        AdaptContentModel model = AdaptationManager.getCurrent().getContentUnitModel(product);
        model.incrementPurchaseCount(1);
    }
    
//    @Test
//    public void findBestSellers() {
//        AdaptUserModel model = AdaptationManager.getCurrent().getUserModel(person);
//        recommendedProducts.forEach(model::setPurchaseTime);
//        List<Long> recommendedProductIds = recommendedProducts.stream().map(AbstractPersistable::getId).collect(Collectors.toList());
//
//        List<String> recommendedIds = adaptUserModelRepository
//                .findByDomainObjectNameAndName(person, Product.class.getName(), PURCHASE_TIME.getName());
//
//        Assert.assertTrue(recommendedIds.size() == baseFilter.getPageSize() || recommendedIds.size() == recommendedProductIds.size());
//
//        recommendedIds.forEach(id -> Assert.assertTrue(recommendedProductIds.contains(Long.valueOf(id))));
//    }
}
