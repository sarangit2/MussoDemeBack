package com.kalanso.mussoback.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalanso.mussoback.Model.Article;
import com.kalanso.mussoback.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    private final String uploadDir = System.getProperty("user.dir") + "/audio/uploads"; // Dossier d'upload

    @GetMapping("/liste")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/liste/{id}")
    public Article getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @PostMapping("/ajout")
    public ResponseEntity<Map<String, String>> createArticleWithAudio(
            @RequestParam("article") String articleJson,
            @RequestParam("file") MultipartFile file) {
        try {
            // Convertir le JSON de l'article en objet Article
            ObjectMapper objectMapper = new ObjectMapper();
            Article article = objectMapper.readValue(articleJson, Article.class);

            // Créer l'article
            Article createdArticle = articleService.createArticle(article);

            // Vérifiez si le dossier d'upload existe, sinon créez-le
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Stocker le fichier audio
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);
            Files.write(path, file.getBytes());

            // Mettre à jour l'URL audio dans l'article
            String audioUrl = "http://localhost:8080/api/articles/audio/" + fileName;
            createdArticle.setAudioUrl(audioUrl);
            articleService.updateArticle(createdArticle.getId(), createdArticle);

            // Préparer la réponse en JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Article créé avec succès");
            response.put("articleId", String.valueOf(createdArticle.getId()));
            response.put("audioUrl", audioUrl);

            return ResponseEntity.ok(response); // Renvoyer la réponse JSON
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors de l'upload du fichier : " + e.getMessage()));
        }
    }


    // Endpoint pour accéder à l'audio
    @GetMapping("/audio/{fileName:.+}")
    public byte[] getAudio(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(uploadDir, fileName);
        return Files.readAllBytes(path);
    }


    @PutMapping("/edit/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article article) {
        return articleService.updateArticle(id, article);
    }

    @DeleteMapping("/del/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}
