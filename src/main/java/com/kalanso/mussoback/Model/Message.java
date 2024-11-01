package com.kalanso.mussoback.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "content", nullable = false) // Assurez-vous que le contenu ne soit pas nul
    private String content;

    @ManyToOne // Relation vers l'utilisateur qui envoie le message
    @JoinColumn(name = "utilisateur_id", nullable = false) // Colonne pour l'expéditeur
    private Utilisateur utilisateur;

    @ManyToOne // Relation vers l'utilisateur destinataire du message (mentor)
    @JoinColumn(name = "destinataire_id", nullable = false) // Colonne pour le destinataire
    private Utilisateur destinataire;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Utilisateur getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Utilisateur destinataire) {
        this.destinataire = destinataire;
    }
}
