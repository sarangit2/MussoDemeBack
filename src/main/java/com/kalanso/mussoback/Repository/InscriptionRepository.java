package com.kalanso.mussoback.Repository;


import com.kalanso.mussoback.Model.Inscription;
import com.kalanso.mussoback.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    // Méthode pour trouver les inscriptions par utilisateur
    List<Inscription> findByUtilisateur(Utilisateur utilisateur);


}

