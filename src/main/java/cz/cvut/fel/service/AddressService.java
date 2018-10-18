package cz.cvut.fel.service;

import cz.cvut.fel.model.Address;
import cz.cvut.fel.web.dto.AddressDto;

public interface AddressService {
    
    AddressDto convertToDto(Address address);
    
    Address convertToModel(AddressDto addressDto);
    
    Address convertToModel(Address address, AddressDto addressDto);
    
    AddressDto getById(long id);
    
    AddressDto getById(String id);
    
    Address save(AddressDto addressDto);
    
    void delete(long id);
}
