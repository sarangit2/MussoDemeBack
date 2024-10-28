package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.Formation;
import com.kalanso.mussoback.Model.Inscription;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Model.status;
import com.kalanso.mussoback.Repository.FormationRepository;
import com.kalanso.mussoback.Repository.InscriptionRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InscriptionService {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private FormationRepository formationRepository;

    // Méthode pour s'inscrire à une formation
    public Inscription registerForFormation(Long formationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Récupérer la formation
            Formation formation = formationRepository.findById(formationId)
                    .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

            // Création de l'inscription
            Inscription inscription = new Inscription();
            inscription.setUtilisateur(utilisateur);
            inscription.setFormation(formation); // Assigner la formation à l'inscription
            inscription.setDateInscription(new Date());
            inscription.setStatus(status.ENCOURS);

            return inscriptionRepository.save(inscription);
        } else {
            throw new RuntimeException("Utilisateur non authentifié");
        }
    }

    public List<Inscription> getUserInscription() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Récupérer les inscriptions de l'utilisateur
            return inscriptionRepository.findByUtilisateur(utilisateur);
        } else {
            throw new RuntimeException("Utilisateur non authentifié");
        }
    }

    public void approveInscription(Long inscriptionId) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        inscription.setStatus(status.APPROUVER);
        inscriptionRepository.save(inscription);
    }

    public Inscription rejectInscription(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));

        inscription.setStatus(status.REJETER);
        return inscriptionRepository.save(inscription);
    }
}
