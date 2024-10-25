package com.kalanso.mussoback.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kalanso.mussoback.Model.Role;
import com.kalanso.mussoback.Model.Utilisateur;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRep {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private Utilisateur utilisateur;
    private Long userId; // Ajoutez cette ligne pour stocker l'ID utilisateur
}
