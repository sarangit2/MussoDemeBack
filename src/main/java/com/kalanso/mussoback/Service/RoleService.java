package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.Role;
import com.kalanso.mussoback.Repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role add(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public Role update(Role role, Long id) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setNom(role.getNom());
                    return roleRepository.save(existingRole);
                })
                .orElse(null);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}

