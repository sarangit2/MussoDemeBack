package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.Role;
import com.kalanso.mussoback.Model.SuperAdmin;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.RoleRepository;
import com.kalanso.mussoback.Repository.SuperAdminRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import com.kalanso.mussoback.Utils.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SuperAdminService implements CrudService<SuperAdmin, Long> {

    private final SuperAdminRepository superAdminRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override

    public SuperAdmin add(SuperAdmin superAdmin, Long roleId) {
        // Vérification de la validité de l'email
        if (!UtilService.isValidEmail(superAdmin.getEmail())) {
            throw new RuntimeException("Votre mail est invalide");
        }

        // Vérifier si l'email est déjà utilisé
        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(superAdmin.getEmail());
        if (utilisateur.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        // Encoder le mot de passe
        String encodePassword = passwordEncoder.encode(superAdmin.getPassword());
        superAdmin.setPassword(encodePassword);

        // Récupérer le rôle à partir de l'identifiant
        Role role = roleRepository.findById(roleId).orElseThrow(() ->
                new RuntimeException("Le rôle avec l'ID " + roleId + " n'existe pas")
        );

        // Associer le rôle à l'utilisateur
        superAdmin.setRole(role);
        return superAdminRepository.save(superAdmin);
    }

    @Override
    public SuperAdmin add(SuperAdmin entity) {
        return null;
    }

    @Override
    public List<SuperAdmin> List() {
        return superAdminRepository.findAll();
    }

    @Override
    public Optional<SuperAdmin> findById(Long id) {
        return superAdminRepository.findById(id);
    }

    @Override
    public SuperAdmin update(SuperAdmin superAdmin, Long id) {
        Optional<SuperAdmin> optionalSuperAdmin = superAdminRepository.findById(id);
        if(optionalSuperAdmin.isPresent()) {
            SuperAdmin superAdminToUpdate = optionalSuperAdmin.get();
            superAdminToUpdate.setNom(superAdmin.getNom());
            superAdminToUpdate.setEmail(superAdmin.getEmail());
            superAdminToUpdate.setPrenom(superAdmin.getPrenom());
            superAdminToUpdate.setPhone(superAdmin.getPhone());
            return superAdminRepository.save(superAdminToUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<SuperAdmin> optionalSuperAdmin = superAdminRepository.findById(id);
        optionalSuperAdmin.ifPresent(superAdminRepository::delete);
    }
}
