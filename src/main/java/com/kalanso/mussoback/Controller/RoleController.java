package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.Role;
import com.kalanso.mussoback.Service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Role ajouter(@RequestBody Role role) {
        return roleService.add(role);
    }

    @GetMapping("/liste")
    public List<Role> lister() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Role modifier(@PathVariable Long id, @RequestBody Role role) {
        return roleService.update(role, id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        roleService.delete(id);
    }
}

