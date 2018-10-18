package cz.cvut.fel.service;

import cz.cvut.fel.model.Person;
import cz.cvut.fel.web.client.dto.ClientDto;
import cz.cvut.fel.web.dto.BasePersonDto;
import cz.cvut.fel.web.dto.PersonDto;

import java.util.Set;

public interface PersonService {
    PersonDto convertToDto(Person person);
    
    BasePersonDto convertToBaseDto(Person person);
    
    Person convertToModel(PersonDto personDto);
    
    Person convertClientToModel(ClientDto clientDto);
    
    Person convertToModel(Person person, PersonDto personDto);
    
    Person convertClientToModel(Person person, ClientDto clientDto);
    
    ClientDto convertToClientDto(Person person);
    
    Person getById(Long id);
    
    Person getByEmail(String email);
    
    Person getAuthorizationPerson();
    
    Person save(PersonDto personDto);
    
    Person saveClient(ClientDto clientDto);
}
