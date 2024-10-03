package com.kalanso.mussoback.Auth;


import com.kalanso.mussoback.DTO.ReqRep;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import com.kalanso.mussoback.SecurityConfig.JwtUtile;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class Login {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UtilisateurRepository utilisateurRepository;
    private JwtUtile jwtUtile;

    @PostMapping("/login")
    public ReqRep login(@RequestBody ReqRep loginRequest) {
        ReqRep response = new ReqRep();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            Utilisateur user = utilisateurRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String jwt = jwtUtile.generateToken(user);
            String refreshToken = jwtUtile.generateRefreshToken(new HashMap<>(), user);

            // Ajouter les détails de l'utilisateur dans la réponse
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setNom(user.getNom());
            response.setPrenom(user.getPrenom());
            response.setEmail(user.getEmail());
            response.setPhone(user.getPhone());
            response.setMessage("Successfully Logged In");

        } catch (Exception e) {
            e.printStackTrace();  // Afficher l'erreur dans la console
            response.setStatusCode(500);
            response.setMessage("Authentication failed: " + e.getMessage());
        }
        return response;
    }
}
