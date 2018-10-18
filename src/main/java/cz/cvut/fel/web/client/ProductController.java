package cz.cvut.fel.web.client;

import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.client.dto.ClientProductDetailDto;
import cz.cvut.fel.web.client.dto.ClientProductFilterDto;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import cz.cvut.fel.web.client.filter.ClientProductFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping(value = "/products")
    public ClientProductFilterDto products(@Valid ClientProductFilter filter) {
        return productService.getByClientFilter(filter);
    }
    
    @GetMapping(value = "/product/{productId}")
    public ClientProductDetailDto product(@PathVariable Long productId) {
        return productService.getProductDetail(productId);
    }
    
    @GetMapping(value = "/products/viewed")
    public ClientProductFilterDto viewedProducts(@Valid BaseClientFilter filter) {
        return productService.getViewedByFilter(filter);
    }
    
    @GetMapping(value = "/products/recommended")
    public ClientProductFilterDto recommendedProducts(@Valid ClientProductFilter filter) {
        return productService.getRecommendedByFilter(filter);
    }
    
    @GetMapping(value = "/products/best_sellers")
    public ClientProductFilterDto bestSellerProducts(@Valid BaseClientFilter filter) {
        return productService.getBestSellersByFilter(filter);
    }
    
    @GetMapping(value = "/product/{productId}/similar")
    public ClientProductFilterDto similarProducts(@PathVariable Long productId, @Valid ClientProductFilter filter) {
        return productService.getSimilarByFilter(productId, filter);
    }
}
