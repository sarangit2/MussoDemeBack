package com.kalanso.mussoback.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalanso.mussoback.Service.FileStorage;
import com.kalanso.mussoback.Service.FormationService;
import com.kalanso.mussoback.Model.Formation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private FormationService formationService;
    private ObjectMapper objectMapper;
    private FileStorage fileStorage;
    @PostMapping("/ajout")
    public ResponseEntity<Formation> createFormation(
            @RequestPart("formation") String formationJson,
            @RequestPart("videoFile") MultipartFile videoFile,
            @RequestPart("imageFile") MultipartFile imageFile) { // Ajoutez ce paramètre

        try {
            Formation formation = objectMapper.readValue(formationJson, Formation.class);

            // Sauvegarder le fichier vidéo
            String videoPath = fileStorage.saveVideo(videoFile);
            //String videoUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
              //      .path("/images/") // Mettez à jour le chemin pour les vidéos
                //    .path(videoPath)
                  //  .toUriString();
            formation.setVideoPath(videoPath); // Enregistrer l'URL pour accéder au fichier vidéo

            // Sauvegarder le fichier image
            String imagePath = fileStorage.saveImage(imageFile);
            //String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
              //      .path("/images/") // Mettez à jour le chemin pour les images
               //     .path(imagePath)
                //    .toUriString();
            formation.setImageUrl(imagePath); // Enregistrer l'URL pour accéder au fichier image

            Formation createdFormation = formationService.addFormation(formation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Erreur de conversion JSON
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Erreurs générales
        }
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }

    @GetMapping("/liste/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Optional<Formation> formation = formationService.getFormationById(id);
        return formation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/recent")
    public List<Formation> getUpcomingFormations() {
        return formationService.getUpcomingFormations(); // Récupérez les 3 dernières formations à venir
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<Map<String, Object>>> getFormationsByMonth() {
        List<Map<String, Object>> formationsByMonth = formationService.getFormationsCountByMonth();
        return ResponseEntity.ok(formationsByMonth);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formationDetails) {
        Formation updatedFormation = formationService.updateFormation(id, formationDetails);
        return ResponseEntity.ok(updatedFormation);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image/{nomImage}")
    public ResponseEntity<Resource> getImage(@PathVariable String nomImage) {
        try {
            // Chemin complet vers l'image dans le dossier "images"
            String imagePath = "images/" + nomImage; // Assurez-vous que ce chemin est correct
            System.out.println("Trying to access image at path: " + imagePath); // Log du chemin de l'image

            FileSystemResource resource = new FileSystemResource(imagePath);

            // Vérifiez si le fichier existe
            if (resource.exists()) {
                System.out.println("Image found: " + imagePath); // Log si l'image est trouvée
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nomImage); // Changez "attachment" à "inline" pour afficher l'image
                return ResponseEntity.ok()
                        .headers(headers)
                        .body((Resource) resource); // Pas besoin de cast ici
            } else {
                System.out.println("Image not found: " + imagePath); // Log si l'image n'est pas trouvée
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("Error occurred while fetching the image: " + e.getMessage()); // Log d'erreur
            e.printStackTrace(); // Affiche la trace de l'erreur pour plus de détails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}



