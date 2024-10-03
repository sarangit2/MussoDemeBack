package com.kalanso.mussoback.Controller;


import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Model.Utilisatrice;
import com.kalanso.mussoback.Service.UtilisateurService;
import com.kalanso.mussoback.Service.UtilisatriceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UtilisatriceController {

    private UtilisatriceService utilisatriceService;
    private UtilisateurService utilisateurService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Utilisatrice ajouter(@RequestBody  Utilisatrice  utilisatrice){
        return utilisatriceService.add(utilisatrice);
    }

    // Méthode pour récupérer les détails de l'utilisateur connecté
    @GetMapping("/me") // Endpoint pour obtenir les détails de l'utilisateur
    public ResponseEntity<Utilisateur> getCurrentUser() {
        Utilisateur user = utilisateurService.getCurrentUser(); // Appel de la méthode du service
        return ResponseEntity.ok(user); // Retourne l'utilisateur au frontend
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.FOUND)
    public List< Utilisatrice> ListerUtilisatrice(){
        return  utilisatriceService.List();
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Utilisatrice> getPersonnelParId(@PathVariable Long id){
        return utilisatriceService.findById(id);
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public  Utilisatrice Modifier(@PathVariable Long id, @RequestBody  Utilisatrice  utilisatrice ){
        return  utilisatriceService.update( utilisatrice,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        utilisatriceService.delete(id);
    }
}

