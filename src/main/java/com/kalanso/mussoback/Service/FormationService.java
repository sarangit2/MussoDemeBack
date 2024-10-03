package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.FormationRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import com.kalanso.mussoback.Model.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    public Formation addFormation(Formation formation) {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(((UserDetails)currentUser).getUsername()).get();
        formation.setUtilisateur(utilisateur);
        return formationRepository.save(formation);
    }

    public Formation updateFormation(Long id, Formation formationDetails) {
        Formation formation = formationRepository.findById(id).orElseThrow(() -> new RuntimeException("Formation not found"));
        formation.setTitre(formationDetails.getTitre());
        formation.setDescription(formationDetails.getDescription());
        formation.setDateDebut(formationDetails.getDateDebut());
        formation.setDateFin(formationDetails.getDateFin());
        formation.setOrganisateur(formationDetails.getOrganisateur());
        return formationRepository.save(formation);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }
}

