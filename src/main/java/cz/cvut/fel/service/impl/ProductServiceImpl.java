package cz.cvut.fel.service.impl;

import cz.cvut.fel.asf.adapt.gomawe.AdaptationManager;
import cz.cvut.fel.model.AdaptUserModel;
import cz.cvut.fel.model.Person;
import cz.cvut.fel.model.Product;
import cz.cvut.fel.repository.AdaptContentModelRepository;
import cz.cvut.fel.repository.AdaptUserModelRepository;
import cz.cvut.fel.repository.PictureRepository;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.service.*;
import cz.cvut.fel.web.client.dto.ClientProductDetailDto;
import cz.cvut.fel.web.client.dto.ClientProductDto;
import cz.cvut.fel.web.client.dto.ClientProductFilterDto;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import cz.cvut.fel.web.client.filter.ClientProductFilter;
import cz.cvut.fel.web.data.ProductData;
import cz.cvut.fel.web.dto.BaseProductDto;
import cz.cvut.fel.web.dto.ProductDto;
import cz.cvut.fel.web.filter.ProductFilter;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static cz.cvut.fel.enums.AttributeName.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final PictureService pictureService;
    private final RatingService ratingService;
    private final PersonService personService;
    private final PictureRepository pictureRepository;
    private final AdaptUserModelRepository adaptUserModelRepository;
    private final AdaptContentModelRepository adaptContentModelRepository;
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, PictureService pictureService, RatingService ratingService, PersonService personService, PictureRepository pictureRepository, AdaptUserModelRepository adaptUserModelRepository, AdaptContentModelRepository adaptContentModelRepository) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.pictureService = pictureService;
        this.ratingService = ratingService;
        this.personService = personService;
        this.pictureRepository = pictureRepository;
        this.adaptUserModelRepository = adaptUserModelRepository;
        this.adaptContentModelRepository = adaptContentModelRepository;
    }
    
    @Override
    public ProductDto convertToDto(Product product) {
        if (product != null) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());
            TreeNode node = categoryService.convertToTreeNode(product.getCategory());
            node.setExpanded(true);
            node.setSelected(true);
            productDto.setCategory(node);
            return productDto;
        }
        return null;
    }
    
    @Override
    public ClientProductDto convertToClientDto(Product product) {
        if (product != null) {
            ClientProductDto clientProductDto = new ClientProductDto();
            clientProductDto.setId(product.getId());
            clientProductDto.setName(product.getName());
            clientProductDto.setPrice(product.getPrice());
            clientProductDto.setPicture(pictureService.convertToClientDto(product.getPicture()));
            return clientProductDto;
        }
        return null;
    }
    
    @Override
    public BaseProductDto convertToBaseDto(Product product) {
        if (product != null) {
            BaseProductDto productDto = new BaseProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            return productDto;
        }
        return null;
    }
    
    @Override
    public Product convertToModel(ProductDto productDto) {
        if (productDto != null) {
            return productDto.getId() != null
                    ? convertToModel(productRepository.findById(productDto.getId()), productDto)
                    : convertToModel(new Product(), productDto);
        }
        return null;
    }
    
    @Override
    public Product convertToModel(Product product, ProductDto productDto) {
        if (product != null && productDto != null) {
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(categoryService.convertToModel(productDto.getCategory()));
            return product;
        }
        return null;
    }
    
    @Override
    public Product convertToModel(BaseProductDto productDto) {
        if (productDto != null) {
            return productDto.getId() != null
                    ? productRepository.findById(productDto.getId())
                    : null;
        }
        return null;
    }
    
    @Override
    public ProductDto getById(long id) {
        return convertToDto(productRepository.findById(id));
    }
    
    @Override
    public ProductData getDataById(long id) {
        return convertToData(productRepository.findById(id));
    }
    
    @Override
    public ProductDto getById(String id) {
        return id == null || id.isEmpty() ? new ProductDto() : getById(Long.valueOf(id));
    }
    
    @Override
    public ProductData getDataById(String id) {
        return id == null || id.isEmpty() ? new ProductData() : getDataById(Long.valueOf(id));
    }
    
    @Override
    public List<BaseProductDto> getByName(String name) {
        return productRepository.findByName(name).stream().map(this::convertToBaseDto).collect(Collectors.toList());
    }
    
    @Override
    public ClientProductDetailDto getProductDetail(Long productId) {
        if (productId != null) {
            Product product = productRepository.findById(productId);
            setProductVisit(product);
            return convertToClientDetailDto(product);
        }
        return null;
    }
    
    @Override
    public void setProductVisit(Product product) {
        Person person = personService.getAuthorizationPerson();
        if (person != null) {
            AdaptUserModel model = AdaptationManager.getCurrent().getUserModel(person);
            model.setProductVisitTime(product);
        }
    }
    
    @Override
    public ClientProductDetailDto convertToClientDetailDto(Product product) {
        if (product != null) {
            ClientProductDetailDto productDto = new ClientProductDetailDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());
            productDto.setRating(ratingService.getProductRating(product.getId()));
            productDto.setPictures(pictureService.getClientPicturesByProduct(product.getId()));
            return productDto;
        }
        return null;
    }
    
    @Override
    public ProductData convertToData(Product product) {
        if (product != null) {
            ProductData productData = new ProductData();
            productData.setId(product.getId());
            productData.setName(product.getName());
            productData.setCategory(product.getCategory().getName());
            productData.setPrice(product.getPrice());
            return productData;
        }
        return null;
    }
    
    @Override
    public void addPrimaryPicture(Long productId, long primaryPictureId) {
        if (productId != null) {
            Product product = productRepository.findById(productId);
            product.setPicture(pictureRepository.findById(primaryPictureId));
            productRepository.save(product);
        }
    }
    
    @Override
    public long getRowCount(ProductFilter filter) {
        return productRepository.findRowCount(filter);
    }
    
    @Override
    public List<ProductData> getByFilter(ProductFilter filter) {
        return productRepository.findByFilter(filter);
    }
    
    @Override
    public ClientProductFilterDto getByClientFilter(ClientProductFilter filter) {
        if (filter.getCategoryId() != null) {
            filter.setCategoryChildren(categoryService.getCategoryWithChildren(filter.getCategoryId()));
        }
        List<ClientProductDto> products = productRepository.findByClientFilter(filter);
        products = products.stream().peek(product -> product.setPicture(pictureService.getById(productRepository.findPictureIdByProduct(product.getId())))).collect(Collectors.toList());
        long totalProducts = productRepository.findRowCountByClientFilter(filter);
        ClientProductFilterDto filterDto = new ClientProductFilterDto();
        filterDto.setProducts(products);
        filterDto.setTotalProducts(totalProducts);
        return filterDto;
    }
    
    @Override
    public ClientProductFilterDto getViewedByFilter(BaseClientFilter filter) {
        Person person = personService.getAuthorizationPerson();
        if (person != null) {
            List<String> products = this.adaptUserModelRepository
                    .findByDomainObjectNameAndName(person, Product.class.getName(), PRODUCT_VISIT_TIME.getName(), filter);
            long totalCount = this.adaptUserModelRepository.findRowCount(person, Product.class.getName(), PRODUCT_VISIT_TIME.getName());
            return convertToFilterDto(products.stream().map(this::getClientDtoById).collect(Collectors.toList()), totalCount);
        }
        return null;
    }
    
    @Override
    public ClientProductFilterDto getRecommendedByFilter(ClientProductFilter filter) {
        Person person = personService.getAuthorizationPerson();
        if (person != null) {
            List<String> products = this.adaptUserModelRepository
                    .findByDomainObjectNameAndName(person, Product.class.getName(), PURCHASE_TIME.getName());
            if (products != null && !products.isEmpty()) {
                List<Long> categories = new ArrayList<>();
                products.forEach(productId -> {
                    Product product = productRepository.findById(Long.valueOf(productId));
                    categories.addAll(categoryService.getCategoryWithChildren(product.getCategory().getId()));
                });
                filter.setCategoryChildren(categories);
                filter.setWithoutProducts(products.stream().map(Long::valueOf).collect(Collectors.toList()));
                return getByClientFilter(filter);
            }
        }
        return null;
    }
    
    public ClientProductFilterDto getBestSellersByFilter(BaseClientFilter filter) {
        Person person = personService.getAuthorizationPerson();
        if (person != null) {
            List<String> products = this.adaptContentModelRepository
                    .findByNameAndFilter(PURCHASE_COUNT.getName(), filter);
            long totalCount = this.adaptContentModelRepository.findRowCount(PURCHASE_COUNT.getName());
            return convertToFilterDto(products.stream().map(this::getClientDtoById).collect(Collectors.toList()), totalCount);
        }
        return null;
    }
    
    @Override
    public ClientProductFilterDto getSimilarByFilter(Long productId, ClientProductFilter filter) {
        if (productId != null) {
            Product product = productRepository.findById(productId);
            filter.setCategoryChildren(categoryService.getCategoryWithChildren(product.getCategory().getId()));
            filter.setWithoutProducts(Collections.singletonList(productId));
            return getByClientFilter(filter);
        }
        return null;
    }
    
    public ClientProductDto getClientDtoById(String productId) {
        if (productId != null && !productId.isEmpty()) {
            Product product = productRepository.findById(Long.valueOf(productId));
            return convertToClientDto(product);
        }
        return null;
    }
    
    public ClientProductFilterDto convertToFilterDto(List<ClientProductDto> products, long totalCount) {
        if (products != null && !products.isEmpty()) {
            ClientProductFilterDto filterDto = new ClientProductFilterDto();
            filterDto.setProducts(products);
            filterDto.setTotalProducts(totalCount);
            return filterDto;
        }
        return null;
    }
    
    @Override
    public ProductDto save(ProductDto productDto) {
        if (productDto != null) {
            Product product = convertToModel(productDto);
            productRepository.save(product);
            return convertToDto(product);
        }
        return null;
    }
    
    @Override
    public void delete(long id) {
        productRepository.delete(productRepository.findById(id));
    }
}
