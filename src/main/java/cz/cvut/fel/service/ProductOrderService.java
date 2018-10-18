package cz.cvut.fel.service;

import cz.cvut.fel.model.ProductOrder;
import cz.cvut.fel.web.client.dto.CartProductDto;
import cz.cvut.fel.web.client.dto.ClientProductOrderDto;
import cz.cvut.fel.web.data.ProductOrderData;
import cz.cvut.fel.web.dto.BaseProductOrderDto;
import cz.cvut.fel.web.dto.ProductOrderDto;
import cz.cvut.fel.web.filter.ProductOrderFilter;

import java.util.List;

public interface ProductOrderService extends BaseService<ProductOrderData, ProductOrderFilter> {
    
    BaseProductOrderDto convertToBaseDto(ProductOrder productOrderOrder);
    
    ProductOrderDto convertToDto(ProductOrder productOrder);
    
    ProductOrder convertToModel(BaseProductOrderDto productOrderDto);
    
    ProductOrder convertToModel(ProductOrder productOrder, BaseProductOrderDto productOrderDto);
    
    BaseProductOrderDto getById(long id);
    
    ProductOrderData getDataById(long id);
    
    BaseProductOrderDto getById(String id);
    
    ClientProductOrderDto getOrder();
    
    ClientProductOrderDto convertToClientDto(ProductOrder productOrder);
    
    ProductOrderData convertToData(ProductOrder productOrder);
    
    void changeShipping(Long shippingId);
    
    void acceptOrder();
    
    List<CartProductDto> getCart();
    
    void changeQuantity(Long productId, Integer quantity);
    
    long save(long productId);
    
    BaseProductOrderDto save(BaseProductOrderDto productOrderDto);
    
    void deleteProduct(Long productId);
    
    void delete(long id);
}
