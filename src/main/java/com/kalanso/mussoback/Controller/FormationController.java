package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Service.FormationService;
import com.kalanso.mussoback.Model.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }


    @GetMapping("/liste/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Optional<Formation> formation = formationService.getFormationById(id);
        return formation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PostMapping("/ajout")
    public Formation createFormation(@RequestBody Formation formation) {

        return formationService.addFormation(formation);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formationDetails) {
        Formation updatedFormation = formationService.updateFormation(id, formationDetails);
        return ResponseEntity.ok(updatedFormation);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}


