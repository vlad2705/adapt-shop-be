package cz.cvut.fel.service;

import cz.cvut.fel.model.OrderItem;
import cz.cvut.fel.model.ProductOrder;
import cz.cvut.fel.web.client.dto.CartProductDto;
import cz.cvut.fel.web.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    
    OrderItemDto convertToBaseDto(OrderItem orderItem);
    
    CartProductDto convertToCartProductDto(OrderItem orderItem);
    
    OrderItem convertToModel(OrderItemDto BaseOrderItemDto, long productOrderId);
    
    OrderItem convertToModel(OrderItem orderItem, OrderItemDto orderItemDto, long productOrderId);
    
    OrderItemDto getBaseDtoById(long id);
    
    OrderItemDto getBaseDtoById(String id);
    
    List<OrderItemDto> getAll();
    
    List<OrderItemDto> getByProductOrder(long productOrderId);
    
    List<CartProductDto> getCartByProductOrder(long productOrderId);
    
    List<OrderItemDto> getByProductOrder(String productOrderId);
    
    void changeQuantity(long productOrderId, long productId, int quantity);
    
    void deleteProduct(long productOrderId, long productId);
    
    OrderItemDto save(OrderItemDto orderItemDto, long productOrderId);
    
    long save(ProductOrder productOrder, long productId);
    
    void delete(long id);
}
