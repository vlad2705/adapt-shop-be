package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Person;

public interface PersonRepository extends IDAO<Person, Long> {
    Person findByEmail(String email);
    
    void save(Person person);
}
