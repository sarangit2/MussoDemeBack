package com.kalanso.mussoback.Repository;


import com.kalanso.mussoback.Model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    List<Formation> findTop3ByDateDebutAfterOrderByDateDebut(LocalDate today);

    @Query("SELECT MONTH(f.dateAjout) as month, COUNT(f) as count FROM Formation f GROUP BY MONTH(f.dateAjout)")
    List<Map<String, Object>> countFormationsByMonth();
}

