package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.OrderItem;
import cz.cvut.fel.model.ProductOrder;
import cz.cvut.fel.repository.OrderItemRepository;
import cz.cvut.fel.repository.ProductOrderRepository;
import cz.cvut.fel.repository.ProductRepository;
import cz.cvut.fel.service.OrderItemService;
import cz.cvut.fel.service.ProductService;
import cz.cvut.fel.web.client.dto.CartProductDto;
import cz.cvut.fel.web.dto.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    
    private final OrderItemRepository orderItemRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    
    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ProductOrderRepository productOrderRepository, ProductService productService, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productOrderRepository = productOrderRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }
    
    @Override
    public OrderItemDto convertToBaseDto(OrderItem orderItem) {
        if (orderItem != null) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setId(orderItem.getId());
            orderItemDto.setProductDto(productService.convertToDto(orderItem.getProduct()));
            orderItemDto.setQuantity(orderItem.getQuantity());
            return orderItemDto;
        }
        return null;
    }
    
    @Override
    public CartProductDto convertToCartProductDto(OrderItem orderItem) {
        if (orderItem != null) {
            CartProductDto cartProductDto = new CartProductDto();
            cartProductDto.setId(orderItem.getId());
            cartProductDto.setProduct(productService.convertToClientDto(orderItem.getProduct()));
            cartProductDto.setQuantity(orderItem.getQuantity());
            return cartProductDto;
        }
        return null;
    }
    
    @Override
    public OrderItem convertToModel(OrderItemDto orderItemDto, long productOrderId) {
        if (orderItemDto != null) {
            return orderItemDto.getId() != null
                    ? convertToModel(orderItemRepository.findById(orderItemDto.getId()), orderItemDto, productOrderId)
                    : convertToModel(new OrderItem(), orderItemDto, productOrderId);
        }
        return null;
    }
    
    @Override
    public OrderItem convertToModel(OrderItem orderItem, OrderItemDto orderItemDto, long productOrderId) {
        if (orderItem != null && orderItemDto != null) {
            orderItem.setProduct(productService.convertToModel(orderItemDto.getProductDto()));
            orderItem.setProductOrder(productOrderRepository.findById(productOrderId));
            orderItem.setQuantity(orderItemDto.getQuantity());
            return orderItem;
        }
        return null;
    }
    
    @Override
    public OrderItemDto getBaseDtoById(long id) {
        return convertToBaseDto(orderItemRepository.findById(id));
    }
    
    @Override
    public OrderItemDto getBaseDtoById(String id) {
        return id == null || id.isEmpty() ? new OrderItemDto() : getBaseDtoById(Long.valueOf(id));
    }
    
    @Override
    public List<OrderItemDto> getAll() {
        return orderItemRepository.findAll().stream().map(this::convertToBaseDto).collect(Collectors.toList());
    }
    
    @Override
    public List<OrderItemDto> getByProductOrder(long productOrderId) {
        return orderItemRepository.findByProductOrder(productOrderId).stream().map(this::convertToBaseDto).collect(Collectors.toList());
    }
    
    @Override
    public List<CartProductDto> getCartByProductOrder(long productOrderId) {
        return orderItemRepository.findByProductOrder(productOrderId).stream().map(this::convertToCartProductDto).collect(Collectors.toList());
    }
    
    @Override
    public List<OrderItemDto> getByProductOrder(String productOrderId) {
        return productOrderId == null || productOrderId.isEmpty() ? Collections.emptyList() : getByProductOrder(Long.valueOf(productOrderId));
    }
    
    @Override
    public void changeQuantity(long productOrderId, long productId, int quantity) {
        List<OrderItem> orderItems = orderItemRepository.findByProductOrderAndProduct(productOrderId, productId);
        if (orderItems == null || orderItems.isEmpty()) {
            throw new RuntimeException("Bad request");
        }
        OrderItem orderItem = orderItems.get(0);
        orderItem.setQuantity(quantity);
        orderItemRepository.save(orderItem);
    }
    
    @Override
    public void deleteProduct(long productOrderId, long productId) {
        List<OrderItem> orderItems = orderItemRepository.findByProductOrderAndProduct(productOrderId, productId);
        if (orderItems == null || orderItems.isEmpty()) {
            throw new RuntimeException("Bad request");
        }
        OrderItem orderItem = orderItems.get(0);
        orderItemRepository.delete(orderItem);
    }
    
    @Override
    public OrderItemDto save(OrderItemDto orderItemDto, long productOrderId) {
        if (orderItemDto != null) {
            OrderItem orderItem = convertToModel(orderItemDto, productOrderId);
            orderItemRepository.save(orderItem);
            return convertToBaseDto(orderItem);
        }
        return null;
    }
    
    @Override
    public long save(ProductOrder productOrder, long productId) {
        List<OrderItem> orderItems = orderItemRepository.findByProductOrderAndProduct(productOrder.getId(), productId);
        OrderItem orderItem; // FIXME: move to converter
        if (orderItems == null || orderItems.size() <= 0) {
            orderItem = new OrderItem();
            orderItem.setProductOrder(productOrder);
            orderItem.setProduct(productRepository.findById(productId));
            orderItem.setQuantity(1);
        } else {
            orderItem = orderItems.get(0);
            orderItem.setQuantity(orderItem.getQuantity() + 1);
        }
        orderItemRepository.save(orderItem);
        Long quantity = orderItemRepository.findQuantityCountByProductOrder(productOrder.getId());
        return quantity != null ? quantity : 0;
    }
    
    @Override
    public void delete(long id) {
        orderItemRepository.delete(orderItemRepository.findById(id));
    }
}
