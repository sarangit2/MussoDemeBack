package com.kalanso.mussoback.Repository;


import com.kalanso.mussoback.Model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    // Possibilité d'ajouter des méthodes de recherche personnalisées si nécessaire
}

