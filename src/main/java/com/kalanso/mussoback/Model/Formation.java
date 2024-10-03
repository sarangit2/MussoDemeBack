package com.kalanso.mussoback.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String organisateur;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur; // Relation avec Utilisateur

    // Constructeurs, getters et setters

    public Formation() {}

    public Formation(String titre, String description, LocalDate dateDebut, LocalDate dateFin, String organisateur, Utilisateur utilisateur) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.organisateur = organisateur;
        this.utilisateur = utilisateur;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }

    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }

    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getOrganisateur() { return organisateur; }

    public void setOrganisateur(String organisateur) { this.organisateur = organisateur; }

    public Utilisateur getUtilisateur() { return utilisateur; }

    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
}


