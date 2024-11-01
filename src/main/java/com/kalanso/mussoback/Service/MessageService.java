package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.Message;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.MessageRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Méthode pour envoyer un message à un mentor (destinataire)
    public Message sendMessage(Message message, Long mentorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        String email = authentication.getName();
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (!utilisateurOpt.isPresent()) {
            throw new RuntimeException("Utilisateur non trouvé pour l'email : " + email);
        }

        if (mentorId == null || mentorId <= 0) {
            throw new IllegalArgumentException("ID de mentor invalide : " + mentorId);
        }

        Optional<Utilisateur> mentorOpt = utilisateurRepository.findById(mentorId);

        if (mentorOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            Utilisateur mentor = mentorOpt.get();

            message.setUtilisateur(utilisateur);
            message.setDestinataire(mentor);

            return messageRepository.save(message);
        } else {
            throw new RuntimeException("Mentor non trouvé avec ID : " + mentorId);
        }
    }
    public Message sendMessage(Message message) {
        // Vérifiez que l'utilisateur a un rôle
        if (message.getUtilisateur() == null || message.getUtilisateur().getRole() == null) {
            throw new IllegalArgumentException("L'utilisateur doit avoir un rôle.");
        }
        return messageRepository.save(message);
    }

    // Méthode pour récupérer les messages envoyés ou reçus par l'utilisateur connecté
    public List<Message> getMessagesByUser() {
        // Récupération de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            // Récupère les messages envoyés ou reçus par cet utilisateur
            return messageRepository.findByUtilisateurOrDestinataire(utilisateur, utilisateur);
        } else {
            throw new RuntimeException("Utilisateur non trouvé pour l'email : " + email);
        }
    }




    // MessageService.java
    public List<Message> getReceivedMessages() {
        // Récupération de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            // Récupérer les messages où l'utilisateur est le destinataire
            return messageRepository.findByDestinataire(utilisateur);
        } else {
            throw new RuntimeException("Utilisateur non trouvé pour l'email : " + email);
        }
    }

    // Nouvelle méthode pour récupérer les messages envoyés par l'utilisateur
    public List<Message> getSentMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            // Récupérer les messages où l'utilisateur est l'expéditeur
            return messageRepository.findByUtilisateur(utilisateur);
        } else {
            throw new RuntimeException("Utilisateur non trouvé pour l'email : " + email);
        }
    }




    public boolean acceptDiscussion(Long userId) {
        // Récupérer l'utilisateur avec l'ID fourni
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(userId);

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();

            // Logique pour accepter la discussion
            // Par exemple, vous pouvez ajouter un champ pour suivre l'état d'acceptation
            utilisateur.setDiscussionAccepted(true); // Vous devez ajouter ce champ dans votre modèle Utilisateur

            // Enregistrer l'utilisateur mis à jour dans la base de données
            utilisateurRepository.save(utilisateur);

            return true; // Discussion acceptée avec succès
        } else {
            // L'utilisateur avec cet ID n'existe pas
            return false; // Échec de l'acceptation de la discussion
        }
    }



}
