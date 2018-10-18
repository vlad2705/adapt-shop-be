package cz.cvut.fel.model.builder;

import cz.cvut.fel.model.Person;

public class TestablePerson extends Person {
    
    public static class Builder {
        private TestablePerson personToBuild;
        
        public Builder() {
            personToBuild = new TestablePerson();
        }
        
        public TestablePerson build() {
            TestablePerson builtPerson = personToBuild;
            personToBuild = new TestablePerson();
            
            return builtPerson;
        }
        
        public TestablePerson.Builder setId(Long id) {
            this.personToBuild.setId(id);
            return this;
        }
        
        public TestablePerson.Builder setEmail(String email) {
            this.personToBuild.setEmail(email);
            return this;
        }
        
        public TestablePerson.Builder setPassword(String password) {
            this.personToBuild.setPassword(password);
            return this;
        }
    }
}
