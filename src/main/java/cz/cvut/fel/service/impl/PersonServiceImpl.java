package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Person;
import cz.cvut.fel.repository.PersonRepository;
import cz.cvut.fel.repository.RoleRepository;
import cz.cvut.fel.service.PersonService;
import cz.cvut.fel.service.RoleService;
import cz.cvut.fel.web.client.dto.ClientDto;
import cz.cvut.fel.web.dto.BasePersonDto;
import cz.cvut.fel.web.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    
    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleService roleService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }
    
    @Override
    public PersonDto convertToDto(Person person) {
        if (person != null) {
            PersonDto personDto = new PersonDto();
            personDto.setId(person.getId());
            personDto.setEmail(person.getEmail());
            personDto.setRoleDtos(person.getRoles().stream().map(roleService::convertToDto).collect(Collectors.toList()));
            return personDto;
        }
        return new PersonDto();
    }
    
    @Override
    public BasePersonDto convertToBaseDto(Person person) {
        if (person != null) {
            PersonDto personDto = new PersonDto();
            personDto.setEmail(person.getEmail());
            return personDto;
        }
        return null;
    }
    
    @Override
    public Person convertToModel(PersonDto personDto) {
        if (personDto != null) {
            return personDto.getId() != null
                    ? convertToModel(personRepository.findById(personDto.getId()), personDto)
                    : convertToModel(new Person(), personDto);
        }
        return null;
    }
    
    @Override
    public Person convertClientToModel(ClientDto clientDto) {
        if (clientDto != null) {
            return clientDto.getId() != null
                    ? convertClientToModel(personRepository.findById(clientDto.getId()), clientDto)
                    : convertClientToModel(new Person(), clientDto);
        }
        return null;
    }
    
    @Override
    public Person convertToModel(Person person, PersonDto personDto) {
        if (personDto != null) {
            person.setEmail(personDto.getEmail());
            if (personDto.getPassword() != null && !personDto.getPassword().isEmpty()) {
                person.setPassword(personDto.getPassword());
            }
            person.setRoles(personDto.getRoleDtos().stream().map(role ->
                    roleRepository.findById(role.getId())).collect(Collectors.toList()));
            return person;
        }
        return null;
    }
    
    @Override
    public Person convertClientToModel(Person person, ClientDto clientDto) {
        if (clientDto != null) {
            person.setEmail(clientDto.getEmail());
            if (clientDto.getPassword() != null) {
                person.setPassword(passwordEncoder.encode(clientDto.getPassword()));
            }
            return person;
        }
        return null;
    }
    
    @Override
    public ClientDto convertToClientDto(Person person) {
        if (person != null) {
            ClientDto clientDto = new ClientDto();
            clientDto.setId(person.getId());
            clientDto.setEmail(person.getEmail());
            return clientDto;
        }
        return null;
    }
    
    @Override
    public Person getById(Long id) {
        if (id != null) {
            return personRepository.findById(id);
        }
        return null;
    }
    
    @Override
    public Person getByEmail(String email) {
        if (email != null && !email.isEmpty()) {
            return personRepository.findByEmail(email);
        }
        return null;
    }
    
    @Override
    public Person getAuthorizationPerson() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }
    
    @Override
    public Person save(PersonDto personDto) {
        if (personDto != null) {
            Person person = convertToModel(personDto);
            personRepository.save(person);
            return person;
        }
        return null;
    }
    
    @Override
    public Person saveClient(ClientDto clientDto) {
        if (clientDto != null) {
            Person person = convertClientToModel(clientDto);
            personRepository.save(person);
            return person;
        }
        return null;
    }
}
