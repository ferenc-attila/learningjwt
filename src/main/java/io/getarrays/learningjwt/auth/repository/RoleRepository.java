package io.getarrays.learningjwt.auth.repository;

import io.getarrays.learningjwt.auth.model.InventoryAppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<InventoryAppRole, Long> {

    Optional<InventoryAppRole> findByName(String roleName);
}
