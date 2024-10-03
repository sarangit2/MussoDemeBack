package com.kalanso.mussoback.Service;


import com.kalanso.mussoback.Model.OffreEmploi;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.OffreEmploiRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffreEmploiService {

    @Autowired
    private OffreEmploiRepository offreEmploiRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Récupérer toutes les offres d'emploi
    public List<OffreEmploi> getAllOffres() {
        return offreEmploiRepository.findAll();
    }

    // Récupérer une offre d'emploi par son ID
    public Optional<OffreEmploi> getOffreById(Long id) {
        return offreEmploiRepository.findById(id);
    }

    // Ajouter une nouvelle offre d'emploi
    public OffreEmploi addOffre(OffreEmploi offreEmploi) {
        System.out.println("Offre à sauvegarder dans le service: " + offreEmploi);
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(((UserDetails)currentUser).getUsername()).get();
        offreEmploi.setUtilisateur(utilisateur);
        return offreEmploiRepository.save(offreEmploi);
    }


    // Mettre à jour une offre d'emploi existante
    public OffreEmploi updateOffre(Long id, OffreEmploi updatedOffre) {
        Optional<OffreEmploi> existingOffre = offreEmploiRepository.findById(id);
        if (existingOffre.isPresent()) {
            OffreEmploi offre = existingOffre.get();
            offre.setTitre(updatedOffre.getTitre());
            offre.setDescription(updatedOffre.getDescription());
            offre.setEntreprise(updatedOffre.getEntreprise());
            offre.setLocalisation(updatedOffre.getLocalisation());
            offre.setDatePublication(updatedOffre.getDatePublication());
            offre.setDateExpiration(updatedOffre.getDateExpiration());
            return offreEmploiRepository.save(offre);
        } else {
            throw new RuntimeException("Offre d'emploi non trouvée");
        }
    }

    // Supprimer une offre d'emploi
    public void deleteOffre(Long id) {
        offreEmploiRepository.deleteById(id);
    }
}
