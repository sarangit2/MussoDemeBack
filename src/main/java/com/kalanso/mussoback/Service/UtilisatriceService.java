package com.kalanso.mussoback.Service;


import com.kalanso.mussoback.Model.Role;
import com.kalanso.mussoback.Model.SuperAdmin;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Model.Utilisatrice;
import com.kalanso.mussoback.Repository.RoleRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import com.kalanso.mussoback.Repository.UtilisatriceRepository;
import com.kalanso.mussoback.Utils.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilisatriceService implements CrudService<Utilisatrice, Long> {

    private final UtilisatriceRepository utilisatriceRepository;
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public SuperAdmin add(SuperAdmin superAdmin, Long roleId) {
        return null;
    }

    @Override
    public Utilisatrice add(Utilisatrice Utilisatrice) {
        if(!UtilService.isValidEmail(Utilisatrice.getEmail())) {
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(Utilisatrice.getEmail());
        if(utilisateur.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        String encodePassword = passwordEncoder.encode(Utilisatrice.getPassword());
        Utilisatrice.setPassword(encodePassword);

        // Vérifier si le rôle "Participant" existe, sinon le créer et sauvegarder
        Role role = roleRepository.findByNom("Utilisatrice").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setNom("Utilisatrice");
            return roleRepository.save(newRole);  // Sauvegarder le rôle avant de l'associer
        });

        Utilisatrice.setRole(role);
        return utilisatriceRepository.save(Utilisatrice);
    }

    @Override
    public List<Utilisatrice> List() {
        return utilisatriceRepository.findAll();
    }

    @Override
    public Optional<Utilisatrice> findById(Long id) {
        return utilisatriceRepository.findById(id);
    }

    @Override
    public Utilisatrice update(Utilisatrice Utilisatrice, Long id) {
        Optional<Utilisatrice> optionalUtilisatrice = utilisatriceRepository.findById(id);
        if(optionalUtilisatrice.isPresent()) {
            Utilisatrice UtilisatriceToUpdate = optionalUtilisatrice.get();
            UtilisatriceToUpdate.setNom(Utilisatrice.getNom());
            UtilisatriceToUpdate.setEmail(Utilisatrice.getEmail());
            UtilisatriceToUpdate.setPrenom(Utilisatrice.getPrenom());
            UtilisatriceToUpdate.setPhone(Utilisatrice.getPhone());
            return utilisatriceRepository.save(UtilisatriceToUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Utilisatrice> optionalUtilisatrice = utilisatriceRepository.findById(id);
        optionalUtilisatrice.ifPresent(utilisatriceRepository::delete);
    }
}

