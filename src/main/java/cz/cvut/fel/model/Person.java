package cz.cvut.fel.model;

import cz.cvut.fel.asf.adapt.gomawe.IUser;
import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "PERSON")
public class Person extends AbstractPersistable<Long> implements IUser<Long> {

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(
                    name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
