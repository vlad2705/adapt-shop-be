package cz.cvut.fel.service.impl;

import cz.cvut.fel.model.Person;
import cz.cvut.fel.model.Role;
import cz.cvut.fel.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;
    
    @Autowired
    public UserDetailServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(
                person.getEmail(), person.getPassword(), true, true, true,
                true, getAuthorities(person.getRoles())
        );
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
    
    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        roles.forEach(
                role -> role.getPrivileges().forEach(
                        privilege -> privileges.add(privilege.getName())
                )
        );
        return privileges;
    }
    
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        return privileges.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
