package cz.cvut.fel.service;

import cz.cvut.fel.model.OrderStatus;
import cz.cvut.fel.web.dto.OrderStatusDto;

import java.util.List;

public interface OrderStatusService {
    
    OrderStatusDto convertToDto(OrderStatus orderStatus);
    
    OrderStatus convertToModel(OrderStatusDto orderStatusDto);
    
    OrderStatusDto getById(long id);
    
    OrderStatusDto getById(String id);
    
    List<OrderStatusDto> getAll();
}
