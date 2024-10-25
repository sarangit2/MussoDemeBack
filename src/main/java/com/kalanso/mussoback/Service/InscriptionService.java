package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.Inscription;
import com.kalanso.mussoback.Model.Utilisateur; // Assurez-vous d'importer votre modèle Utilisateur
import com.kalanso.mussoback.Repository.InscriptionRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository; // Assurez-vous d'importer votre repository Utilisateur
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
    private UtilisateurRepository utilisateurRepository; // Injection du repository utilisateur

    // Méthode pour s'inscrire à une formation
    public Inscription registerForFormation(Long formationId) {
        // Récupérer l'utilisateur actuellement authentifié
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(((UserDetails) currentUser).getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")); // Gestion des erreurs si l'utilisateur n'est pas trouvé

        // Création de l'inscription
        Inscription inscription = new Inscription();
        inscription.setFormationId(formationId);
        inscription.setUserId(utilisateur.getId()); // Assigner l'ID de l'utilisateur à l'inscription
        inscription.setDateInscription(new Date());

        return inscriptionRepository.save(inscription); // Enregistrer l'inscription
    }


}
