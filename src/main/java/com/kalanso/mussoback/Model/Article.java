package com.kalanso.mussoback.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    @Enumerated(EnumType.STRING) // Utilisation de EnumType.STRING pour stocker le nom de l'énumération
    private Type type; // Changement du type de String à ArticleType
    private String audioUrl;  // Ajout du champ audioUrl
    private LocalDate datePublication = LocalDate.now();
    private LocalDate dateAjout;  // Nouveau champ pour la date d'ajout

    @ManyToOne
    @JoinColumn(name = "utilisateur", nullable = true)
    private Utilisateur utilisateur;  // Relation avec l'utilisateur qui a posté l'offre

    // Méthode pour définir la date d'ajout automatiquement
    @PrePersist
    protected void onCreate() {
        this.dateAjout = LocalDate.now();  // Assigner la date actuelle lors de la création
    }

    // Getters et Setters générés par Lombok
}
