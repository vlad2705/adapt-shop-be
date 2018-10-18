package cz.cvut.fel.service;

import cz.cvut.fel.model.Shipping;
import cz.cvut.fel.web.client.dto.ClientShippingDto;

import java.util.List;

public interface ShippingService {
    
    ClientShippingDto convertToClientDto(Shipping shipping);
    
    List<ClientShippingDto> getAll();
}
