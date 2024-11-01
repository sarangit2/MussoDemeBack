package com.kalanso.mussoback.Repository;

import com.kalanso.mussoback.Model.Message;
import com.kalanso.mussoback.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByUtilisateur(Utilisateur utilisateur);

    List<Message> findByUtilisateurOrDestinataire(Utilisateur utilisateur, Utilisateur utilisateur1);


    List<Message> findByDestinataire(Utilisateur utilisateur);
}

