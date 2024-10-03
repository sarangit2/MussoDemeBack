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
    private String type;
    private LocalDate datePublication;

    @ManyToOne
    @JoinColumn(name = "utilisateur",nullable = true)
    private Utilisateur utilisateur;  // Relation avec l'utilisateur qui a posté l'offre

    // Getters et Setters
}
