package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Shipping;
import cz.cvut.fel.repository.ShippingRepository;
import cz.cvut.fel.service.ShippingService;
import cz.cvut.fel.web.client.dto.ClientShippingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShippingServiceImpl implements ShippingService {
    
    private final ShippingRepository shippingRepository;
    
    @Autowired
    public ShippingServiceImpl(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }
    
    @Override
    public ClientShippingDto convertToClientDto(Shipping shipping) {
        if (shipping != null) {
            ClientShippingDto shippingDto = new ClientShippingDto();
            shippingDto.setId(shipping.getId());
            shippingDto.setName(shipping.getName());
            shippingDto.setCost(shipping.getCost());
            return shippingDto;
        }
        return null;
    }
    
    @Override
    public List<ClientShippingDto> getAll() {
        return shippingRepository.findAll().stream().map(this::convertToClientDto).collect(Collectors.toList());
    }
}
