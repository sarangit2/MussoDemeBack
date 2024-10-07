package com.kalanso.mussoback.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "offres_emploi")
@Data
@NoArgsConstructor
public class OffreEmploi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String entreprise;
    private String localisation;
    private LocalDate datePublication;
    private LocalDate dateExpiration;
    private LocalDate dateAjout;  // Nouveau champ pour la date d'ajout

    @ManyToOne
    @JoinColumn(name = "utilisateur", nullable = true)
    private Utilisateur utilisateur;  // Relation avec l'utilisateur qui a posté l'article

    // Méthode pour définir la date d'ajout automatiquement
    @PrePersist
    protected void onCreate() {
        this.dateAjout = LocalDate.now();  // Assigner la date actuelle lors de la création
    }


    // Getters et Setters

}
