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
import org.springframework.http.MediaType;
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

    private final FormationService formationService;
    private final ObjectMapper objectMapper;
    private final FileStorage fileStorage;

    @PostMapping(value = "/ajout", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Formation> createFormation(
            @RequestPart("formation") String formationJson,
            @RequestPart("videoFile") MultipartFile videoFile,
            @RequestPart("imageFile") MultipartFile imageFile,
            @RequestPart("pdfFile") MultipartFile pdfFile) { // Ajout du paramètre pdfFile

        try {
            System.out.println("start add");
            Formation formation = objectMapper.readValue(formationJson, Formation.class);
            System.out.println("formation :" + formation);

            // Sauvegarder le fichier vidéo
            String videoPath = fileStorage.saveVideo(videoFile);
            System.out.println("videoPath :" + videoPath);
            formation.setVideoPath(videoPath);

            // Sauvegarder le fichier image
            String imagePath = fileStorage.saveImage(imageFile);
            System.out.println("imagePath :" + imagePath);
            formation.setImageUrl(imagePath);

            // Sauvegarder le fichier PDF
            String pdfPath = fileStorage.savePDF(pdfFile); // Méthode pour sauvegarder le PDF
            System.out.println("pdfPath :" + pdfPath);
            formation.setPdfPath(pdfPath); // Assigner le chemin du PDF à l'objet formation

            Formation createdFormation = formationService.addFormation(formation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/mesformations")
    public ResponseEntity<List<Formation>> getFormationsForConnectedUser() {
        List<Formation> formations = formationService.getFormationsForConnectedUser();
        return ResponseEntity.ok(formations);
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
        return formationService.getUpcomingFormations();
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<Map<String, Object>>> getFormationsByMonth() {
        List<Map<String, Object>> formationsByMonth = formationService.getFormationsCountByMonth();
        return ResponseEntity.ok(formationsByMonth);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Formation> updateFormation(
            @PathVariable Long id,
            @RequestPart(value = "formation", required = false) String formationJson,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "pdfFile", required = false) MultipartFile pdfFile) { // Ajout pour le PDF

        try {
            Formation updatedFormation = formationService.getFormationById(id)
                    .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

            if (formationJson != null) {
                Formation formationDetails = objectMapper.readValue(formationJson, Formation.class);
                updatedFormation.setTitre(formationDetails.getTitre());
                updatedFormation.setDescription(formationDetails.getDescription());
                // Mettez à jour d'autres champs si nécessaire
            }

            // Gérer la mise à jour des fichiers vidéo, image et PDF
            if (videoFile != null && !videoFile.isEmpty()) {
                String videoPath = fileStorage.saveVideo(videoFile);
                updatedFormation.setVideoPath(videoPath);
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = fileStorage.saveImage(imageFile);
                updatedFormation.setImageUrl(imagePath);
            }

            if (pdfFile != null && !pdfFile.isEmpty()) {
                String pdfPath = fileStorage.savePDF(pdfFile);
                updatedFormation.setPdfPath(pdfPath); // Assurez-vous que cette propriété existe dans l'entité Formation
            }

            Formation savedFormation = formationService.updateFormation(id, updatedFormation);
            return ResponseEntity.ok(savedFormation);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image/{nomImage}")
    public ResponseEntity<Resource> getImage(@PathVariable String nomImage) {
        try {
            String imagePath = "images/" + nomImage;
            System.out.println("Trying to access image at path: " + imagePath);

            FileSystemResource resource = new FileSystemResource(imagePath);

            if (resource.exists()) {
                System.out.println("Image found: " + imagePath);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nomImage);
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                System.out.println("Image not found: " + imagePath);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("Error occurred while fetching the image: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
