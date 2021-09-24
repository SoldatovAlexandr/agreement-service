package edu.strongsubgroup.agreement.repository;

import edu.strongsubgroup.agreement.common.UserRoles;
import edu.strongsubgroup.agreement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRoles name);
}
