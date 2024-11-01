package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.Message;
import com.kalanso.mussoback.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Endpoint pour envoyer un message à un mentor spécifique
    @PostMapping("/send/{mentorId}")
    public ResponseEntity<Message> sendMessage(@PathVariable Long mentorId, @RequestBody Message message) {
        Message savedMessage = messageService.sendMessage(message, mentorId);
        return ResponseEntity.ok(savedMessage);
    }



    // Endpoint pour récupérer les messages envoyés ou reçus par l'utilisateur connecté
    @GetMapping("/received")
    public ResponseEntity<List<Message>> getReceivedMessages() {
        List<Message> messages = messageService.getReceivedMessages();
        return ResponseEntity.ok(messages);
    }


    // Endpoint pour accepter la discussion avec un utilisateur spécifique
    @PostMapping("/accept/{userId}")
    public ResponseEntity<String> acceptDiscussion(@PathVariable Long userId) {
        boolean isAccepted = messageService.acceptDiscussion(userId);
        if (isAccepted) {
            return ResponseEntity.ok("Discussion acceptée avec l'utilisateur : " + userId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'acceptation de la discussion");
        }
    }


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.sendMessage(message);
            return new ResponseEntity<>(savedMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Nouvel endpoint pour récupérer les messages envoyés par l'utilisateur
    @GetMapping("/sent")
    public ResponseEntity<List<Message>> getSentMessages() {
        List<Message> messages = messageService.getSentMessages();
        return ResponseEntity.ok(messages);
    }

}
