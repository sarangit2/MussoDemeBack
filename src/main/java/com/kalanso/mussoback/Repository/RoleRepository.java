package com.kalanso.mussoback.Repository;


import com.kalanso.mussoback.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Optional<Role> findByNom(String nom);
}
