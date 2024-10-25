package com.kalanso.mussoback.Service;


import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByEmail(username).orElseThrow();
    }



    // Méthode pour récupérer l'utilisateur connecté
    public Utilisateur getCurrentUser() {
        // Récupérer l'utilisateur connecté à partir de la sécurité
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername(); // Supposons que l'email est utilisé comme identifiant

        // Retourner l'utilisateur de la base de données
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
