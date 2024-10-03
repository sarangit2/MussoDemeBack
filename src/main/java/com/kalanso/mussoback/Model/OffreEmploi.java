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

    @ManyToOne
    @JoinColumn(name = "utilisateur",nullable = true)
    private Utilisateur utilisateur;  // Relation avec l'utilisateur qui a posté l'offre

    // Constructeurs, getters et setters

   /* public OffreEmploi() {}

    public OffreEmploi(String titre, String description, String entreprise, String localisation, LocalDate datePublication, LocalDate dateExpiration, Utilisateur utilisateur) {
        this.titre = titre;
        this.description = description;
        this.entreprise = entreprise;
        this.localisation = localisation;
        this.datePublication = datePublication;
        this.dateExpiration = dateExpiration;
        this.utilisateur = utilisateur;
    }*/

    // Getters et Setters
    /*public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getEntreprise() { return entreprise; }

    public void setEntreprise(String entreprise) { this.entreprise = entreprise; }

    public String getLocalisation() { return localisation; }

    public void setLocalisation(String localisation) { this.localisation = localisation; }

    public LocalDate getDatePublication() { return datePublication; }

    public void setDatePublication(LocalDate datePublication) { this.datePublication = datePublication; }

    public LocalDate getDateExpiration() { return dateExpiration; }

    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }

    public Utilisateur getUtilisateur() { return utilisateur; }

    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }*/
}
