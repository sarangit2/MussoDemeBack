package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.SuperAdmin;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Service.SuperAdminService;
import com.kalanso.mussoback.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/superadmin")
@AllArgsConstructor
public class SuperAdminController {

    private SuperAdminService superAdminService;
    private UtilisateurService utilisateurService;

    @PostMapping("/ajout/{roleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SuperAdmin ajouter(@RequestBody SuperAdmin superAdmin, @PathVariable Long roleId) {
        return superAdminService.add(superAdmin, roleId);
    }

    @GetMapping("/me")
    public ResponseEntity<Utilisateur> getCurrentUser() {
        Utilisateur user = utilisateurService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/listeSuperAdmin")
    public List<SuperAdmin> ListerSuperAdmin() {
        return superAdminService.List();
    }

    @GetMapping("/listesuperadmin/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<SuperAdmin> getPersonnelParId(@PathVariable Long id) {
        return superAdminService.findById(id);
    }



    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SuperAdmin Modifier(@PathVariable Long id, @RequestBody SuperAdmin superAdmin) {
        return superAdminService.update(superAdmin, id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        superAdminService.delete(id);
    }
}
