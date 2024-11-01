package com.kalanso.mussoback.Model;

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
    private LocalDate dateAjout;  // Champ pour la date d'ajout

    @Enumerated(EnumType.STRING)
    private Categorie categorie; // Enum pour la catégorie

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur; // Relation avec Utilisateur

    private String videoPath; // Champ pour le chemin de la vidéo
    private String imageUrl;  // Chemin ou URL de l'image
    private String pdfPath;   // Champ pour le chemin du PDF

    // Constructeurs, getters et setters

    public Formation() {}

    public Formation(String titre, String description, LocalDate dateDebut, LocalDate dateFin, String organisateur, Categorie categorie, Utilisateur utilisateur, String videoPath, String imageUrl, String pdfPath) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.organisateur = organisateur;
        this.categorie = categorie;
        this.utilisateur = utilisateur;
        this.videoPath = videoPath;
        this.imageUrl = imageUrl;  // Ajouter imageUrl ici
        this.pdfPath = pdfPath;     // Ajouter pdfPath ici
    }

    @PrePersist
    protected void onCreate() {
        this.dateAjout = LocalDate.now(); // Définir automatiquement la date d'ajout
    }

    // Getters et setters
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

    public LocalDate getDateAjout() { return dateAjout; } // Getter pour dateAjout

    public Categorie getCategorie() { return categorie; } // Getter pour catégorie

    public void setCategorie(Categorie categorie) { this.categorie = categorie; } // Setter pour catégorie

    public Utilisateur getUtilisateur() { return utilisateur; }

    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public String getVideoPath() { return videoPath; } // Getter pour videoPath

    public void setVideoPath(String videoPath) { this.videoPath = videoPath; } // Setter pour videoPath

    public String getImageUrl() { return imageUrl; }  // Getter pour imageUrl

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }  // Setter pour imageUrl

    public String getPdfPath() { return pdfPath; } // Getter pour pdfPath

    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; } // Setter pour pdfPath
}
