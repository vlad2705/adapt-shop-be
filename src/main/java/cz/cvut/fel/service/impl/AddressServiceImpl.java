package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Address;
import cz.cvut.fel.repository.AddressRepository;
import cz.cvut.fel.service.AddressService;
import cz.cvut.fel.web.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    
    private final AddressRepository addressRepository;
    
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    
    @Override
    public AddressDto convertToDto(Address address) {
        if (address != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setId(address.getId());
            addressDto.setAddress(address.getAddress());
            addressDto.setPostalCode(address.getPostalCode());
            addressDto.setCity(address.getCity());
            addressDto.setCountry(address.getCountry());
            return addressDto;
        }
        return null;
    }
    
    @Override
    public Address convertToModel(AddressDto addressDto) {
        if (addressDto != null) {
            return addressDto.getId() != null
                    ? convertToModel(addressRepository.findById(addressDto.getId()), addressDto)
                    : convertToModel(new Address(), addressDto);
        }
        return null;
    }
    
    @Override
    public Address convertToModel(Address address, AddressDto addressDto) {
        if (address != null && addressDto != null) {
            address.setAddress(addressDto.getAddress());
            address.setPostalCode(addressDto.getPostalCode());
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
            return address;
        }
        return null;
    }
    
    @Override
    public AddressDto getById(long id) {
        return convertToDto(addressRepository.findById(id));
    }
    
    @Override
    public AddressDto getById(String id) {
        return id == null || id.isEmpty() ? new AddressDto() : getById(Long.valueOf(id));
    }
    
    @Override
    public Address save(AddressDto addressDto) {
        if (addressDto != null) {
            Address address = convertToModel(addressDto);
            addressRepository.save(address);
            return address;
        }
        return null;
    }
    
    @Override
    public void delete(long id) {
        addressRepository.delete(addressRepository.findById(id));
    }
}
