package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Roles findRolesByRoleName(String roleName);
}
