package cz.cvut.fel.service;

import cz.cvut.fel.model.Product;
import cz.cvut.fel.web.client.dto.ClientProductDetailDto;
import cz.cvut.fel.web.client.dto.ClientProductDto;
import cz.cvut.fel.web.client.dto.ClientProductFilterDto;
import cz.cvut.fel.web.client.filter.BaseClientFilter;
import cz.cvut.fel.web.client.filter.ClientProductFilter;
import cz.cvut.fel.web.data.ProductData;
import cz.cvut.fel.web.dto.BaseProductDto;
import cz.cvut.fel.web.dto.ProductDto;
import cz.cvut.fel.web.filter.ProductFilter;

import java.util.List;

public interface ProductService extends BaseService<ProductData, ProductFilter> {
    
    ProductDto convertToDto(Product product);
    
    ClientProductDto convertToClientDto(Product product);
    
    BaseProductDto convertToBaseDto(Product product);
    
    Product convertToModel(ProductDto productDto);
    
    Product convertToModel(Product product, ProductDto productDto);
    
    Product convertToModel(BaseProductDto productDto);
    
    ProductDto getById(long id);
    
    ProductData getDataById(long id);
    
    ProductDto getById(String id);
    
    List<BaseProductDto> getByName(String name);
    
    ClientProductDetailDto getProductDetail(Long productId);
    
    void setProductVisit(Product product);
    
    ClientProductDetailDto convertToClientDetailDto(Product product);
    
    ProductData convertToData(Product product);
    
    void addPrimaryPicture(Long productId, long primaryPictureId);
    
    ClientProductFilterDto getByClientFilter(ClientProductFilter filter);
    
    ClientProductFilterDto getViewedByFilter(BaseClientFilter filter);
    
    ClientProductFilterDto getRecommendedByFilter(ClientProductFilter filter);
    
    ClientProductFilterDto getBestSellersByFilter(BaseClientFilter filter);
    
    ClientProductFilterDto getSimilarByFilter(Long productId, ClientProductFilter filter);
    
    ProductDto save(ProductDto productDto);
    
    void delete(long id);
}
