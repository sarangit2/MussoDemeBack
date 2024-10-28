package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.Formation;
import com.kalanso.mussoback.Model.Inscription;
import com.kalanso.mussoback.Service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    // Méthode pour s'inscrire à une formation
    @PostMapping("/inscription/{formationId}")
    public ResponseEntity<Inscription> registerForFormation(@PathVariable Long formationId) {
        try {
            Inscription inscription = inscriptionService.registerForFormation(formationId);
            return new ResponseEntity<>(inscription, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/inscriptions")
    public ResponseEntity<List<Inscription>> getUserInscriptions() {
        try {
            List<Inscription> inscriptions = inscriptionService.getUserInscription(); // Obtenir les inscriptions
            return new ResponseEntity<>(inscriptions, HttpStatus.OK); // Retourner la liste des inscriptions
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED); // Gérer les exceptions
        }
    }


    // Nouveau point de terminaison pour approuver une demande d'inscription
    @PutMapping("/inscription/approve/{inscriptionId}")
    public ResponseEntity<String> approveInscription(@PathVariable Long inscriptionId) {
        try {
            inscriptionService.approveInscription(inscriptionId);
            return new ResponseEntity<>("Inscription approuvée avec succès.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Échec de l'approbation de l'inscription.", HttpStatus.NOT_FOUND);
        }
    }

    // Nouveau point de terminaison pour rejeter une inscription
    @PutMapping("/inscription/rejeter/{id}")
    public ResponseEntity<Inscription> rejectInscription(@PathVariable Long id) {
        try {
            Inscription inscription = inscriptionService.rejectInscription(id);
            return new ResponseEntity<>(inscription, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
