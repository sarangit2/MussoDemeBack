package com.kalanso.mussoback.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorage {

    // Dossiers de destination pour les fichiers
    private final String dossierAudio = "uploads/audio/";
    private final String images = "images/";
    private final String dossierText = "uploads/text/";
    private final String dossierPDF = "images/"; // Dossier pour les PDF
    private final String lien = "http://localhost:8080";  // URL publique pour le frontend

    // Sauvegarde un fichier vidéo
    public String saveVideo(MultipartFile fichier) throws IOException {
        return saveFile(fichier, images, "video");
    }

    // Sauvegarde un fichier image
    public String saveImage(MultipartFile fichier) throws IOException {
        return saveFile(fichier, images, "image");
    }

    // Sauvegarde un fichier texte
    public String saveText(MultipartFile fichier) throws IOException {
        return saveFile(fichier, dossierText, "text");
    }

    // Sauvegarde un fichier PDF
    public String savePDF(MultipartFile fichier) throws IOException {
        return saveFile(fichier, dossierPDF, "pdf");
    }

    // Méthode générique pour sauvegarder un fichier
    private String saveFile(MultipartFile fichier, String dossier, String type) throws IOException {
        // Vérifie si le dossier de destination existe, sinon le créer
        Path cheminDossier = Paths.get(dossier);
        if (!Files.exists(cheminDossier)) {
            Files.createDirectories(cheminDossier);
        }

        // Vérifie que le fichier n'est pas vide et a un nom valide
        String nomFichier = fichier.getOriginalFilename();
        if (nomFichier == null || nomFichier.isEmpty()) {
            throw new IOException("Le nom du fichier est invalide.");
        }

        // Vérifie que l'extension du fichier est valide
        if (type.equals("video") && !isValidVideoFile(nomFichier)) {
            throw new IOException("Le fichier vidéo n'est pas valide.");
        }
        if (type.equals("text") && !isValidTextFile(nomFichier)) {
            throw new IOException("Le fichier texte n'est pas valide.");
        }
        if (type.equals("image") && !isValidImageFile(nomFichier)) {
            throw new IOException("Le fichier image n'est pas valide.");
        }
        if (type.equals("pdf") && !isValidPDFFile(nomFichier)) {
            throw new IOException("Le fichier PDF n'est pas valide.");
        }

        // Génère un nom de fichier unique pour éviter les conflits
        String extension = getFileExtension(nomFichier);
        String nomFichierUnique = UUID.randomUUID().toString() + "." + extension;
        Path cheminFichier = cheminDossier.resolve(nomFichierUnique);

        // Sauvegarde le fichier sur le disque
        Files.copy(fichier.getInputStream(), cheminFichier);

        // Retourne l'URL du fichier sauvegardé pour le frontend
        return lien + "/" + dossier + nomFichierUnique;
    }

    // Vérifie que l'extension du fichier est valide pour les fichiers texte
    private boolean isValidTextFile(String nomFichier) {
        String extension = getFileExtension(nomFichier).toLowerCase();
        return extension.equals("txt") || extension.equals("pdf");
    }

    // Vérifie que l'extension du fichier est valide pour les fichiers PDF
    private boolean isValidPDFFile(String nomFichier) {
        String extension = getFileExtension(nomFichier).toLowerCase();
        return extension.equals("pdf");
    }

    // Supprimer un fichier (basé sur l'URL sauvegardée)
    public void deleteFileByUrl(String fileUrl) throws IOException {
        // Supprime le préfixe de l'URL pour obtenir le chemin relatif
        String relativePath = fileUrl.replace(lien + "/", "");
        Path cheminFichier = Paths.get(relativePath);

        // Supprimer le fichier si présent
        Files.deleteIfExists(cheminFichier);
    }

    // Vérifie si le fichier vidéo a une extension valide
    private boolean isValidVideoFile(String nomFichier) {
        String extension = getFileExtension(nomFichier).toLowerCase();
        return extension.equals("mp4") || extension.equals("avi");
    }

    // Vérifie si le fichier image a une extension valide
    private boolean isValidImageFile(String nomFichier) {
        String extension = getFileExtension(nomFichier).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
    }

    // Extrait l'extension du fichier
    private String getFileExtension(String nomFichier) {
        int lastIndexOfDot = nomFichier.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            throw new IllegalArgumentException("Le fichier ne semble pas avoir d'extension.");
        }
        return nomFichier.substring(lastIndexOfDot + 1);
    }
}
