package com.kalanso.mussoback.Repository;


import com.kalanso.mussoback.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String Email);

    List<Utilisateur> findByRole_Nom(String MENTOR);
}
