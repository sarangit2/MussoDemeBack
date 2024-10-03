package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.OffreEmploi;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Service.OffreEmploiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/offres")
public class OffreEmploiController {

    @Autowired
    private OffreEmploiService offreEmploiService;

    // Récupérer toutes les offres d'emploi
    @GetMapping("/liste")
    public List<OffreEmploi> getAllOffres() {
        return offreEmploiService.getAllOffres();
    }

    // Récupérer une offre d'emploi par ID
    @GetMapping("/liste/{id}")
    public ResponseEntity<OffreEmploi> getOffreById(@PathVariable Long id) {
        Optional<OffreEmploi> offre = offreEmploiService.getOffreById(id);
        return offre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer une nouvelle offre d'emploi
    @PostMapping("/ajout")
    public OffreEmploi createOffre(@RequestBody OffreEmploi offreEmploi) {
        // Récupérer l'utilisateur connecté

          // Associer l'offre à l'utilisateur connecté
        return offreEmploiService.addOffre(offreEmploi);
    }

    // Mettre à jour une offre d'emploi existante
    @PutMapping("/modifier/{id}")
    public ResponseEntity<OffreEmploi> updateOffre(@PathVariable Long id, @RequestBody OffreEmploi offreEmploi) {
        OffreEmploi updatedOffre = offreEmploiService.updateOffre(id, offreEmploi);
        return ResponseEntity.ok(updatedOffre);
    }

    // Supprimer une offre d'emploi
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        offreEmploiService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }
}

