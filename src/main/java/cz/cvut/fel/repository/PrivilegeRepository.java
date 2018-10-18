package cz.cvut.fel.repository;

import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends IDAO<Privilege, Long> {
    Privilege findByName(String name);
}
