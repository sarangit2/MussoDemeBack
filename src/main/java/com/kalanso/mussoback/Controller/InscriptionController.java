package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.Inscription;
import com.kalanso.mussoback.Service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/") // Point de terminaison de l'API pour les inscriptions
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    // Méthode pour s'inscrire à une formation
    @PostMapping("/inscription/{formationId}")
    public ResponseEntity<Inscription> registerForFormation(@PathVariable Long formationId) {
        try {
            Inscription inscription = inscriptionService.registerForFormation(formationId);
            return new ResponseEntity<>(inscription, HttpStatus.CREATED); // Retourner 201 Created
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Retourner 404 si l'utilisateur n'est pas trouvé
        }
    }


}
