package cz.cvut.fel.model;

import cz.cvut.fel.asf.persistence.jpa.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ROLE")
public class Role extends AbstractPersistable<Long> {

    @Column(name = "NAME")
    private String name;

    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
