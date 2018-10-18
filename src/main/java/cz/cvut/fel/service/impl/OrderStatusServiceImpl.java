package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.OrderStatus;
import cz.cvut.fel.repository.OrderStatusRepository;
import cz.cvut.fel.service.OrderStatusService;
import cz.cvut.fel.web.dto.OrderStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderStatusServiceImpl implements OrderStatusService {
    
    private final OrderStatusRepository orderStatusRepository;
    
    @Autowired
    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }
    
    @Override
    public OrderStatusDto convertToDto(OrderStatus orderStatus) {
        if (orderStatus != null) {
            OrderStatusDto orderStatusDto = new OrderStatusDto();
            orderStatusDto.setId(orderStatus.getId());
            orderStatusDto.setName(orderStatus.getName());
            return orderStatusDto;
        }
        return null;
    }
    
    @Override
    public OrderStatus convertToModel(OrderStatusDto orderStatusDto) {
        if (orderStatusDto != null) {
            return orderStatusDto.getId() != null
                    ? orderStatusRepository.findById(orderStatusDto.getId())
                    : null;
        }
        return null;
    }
    
    @Override
    public OrderStatusDto getById(long id) {
        return convertToDto(orderStatusRepository.findById(id));
    }
    
    @Override
    public OrderStatusDto getById(String id) {
        return id == null || id.isEmpty() ? new OrderStatusDto() : getById(Long.valueOf(id));
    }
    
    @Override
    public List<OrderStatusDto> getAll() {
        return orderStatusRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
