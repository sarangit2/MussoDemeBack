package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.FormationRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import com.kalanso.mussoback.Model.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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


    public List<Map<String, Object>> getFormationsCountByMonth() {
        return formationRepository.countFormationsByMonth();
    }


    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    public Formation addFormation(Formation formation) {
        // Vérifiez si la catégorie est null
        if (formation.getCategorie() == null) {
            throw new IllegalArgumentException("La catégorie ne peut pas être null.");
        }

        // Récupérer l'utilisateur actuellement authentifié
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(((UserDetails) currentUser).getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")); // Gestion des erreurs si l'utilisateur n'est pas trouvé

        formation.setUtilisateur(utilisateur); // Assigner l'utilisateur à la formation
        return formationRepository.save(formation); // Enregistrer la formation
    }


    public List<Formation> getUpcomingFormations() {
        LocalDate today = LocalDate.now();
        return formationRepository.findTop3ByDateDebutAfterOrderByDateDebut(today); // Exemple d'utilisation d'une méthode de repository
    }

    // Méthode pour récupérer les formations de l'utilisateur connecté
    public List<Formation> getFormationsForConnectedUser() {
        // Récupération de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Récupère l'email de l'utilisateur connecté

        // Recherchez l'utilisateur en fonction de son email
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (utilisateurOpt.isPresent()) {
            return List.copyOf(utilisateurOpt.get().getFormations());
        } else {
            throw new RuntimeException("Utilisateur non trouvé pour l'email : " + email);
        }
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

