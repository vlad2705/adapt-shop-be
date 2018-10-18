package cz.cvut.fel.model;

import cz.cvut.fel.model.builder.TestablePerson;

public class PersonTest {

    public static Person defaultPerson()
    {
        return create(1L, "petr.tester@gmail.com", "tester123");
    }
    
    public static Person create(Long id, String email, String password)
    {
        return new TestablePerson.Builder()
                .setId(id)
                .setEmail(email)
                .setPassword(password)
                .build();
    }
}
