package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.Article;
import com.kalanso.mussoback.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    public Article createArticle(@RequestBody Article article) {
        return articleService.createArticle(article);
    }

    @PutMapping("/edit/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article article) {
        return articleService.updateArticle(id, article);
    }

    @DeleteMapping("/del/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }

    @PostMapping("/ajout/audio/{id}")
    public String uploadAudio(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
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
            Article article = articleService.getArticleById(id);
            if (article != null) {
                String audioUrl = "http://localhost:8080/api/articles/audio/" + fileName; // URL de l'audio
                article.setAudioUrl(audioUrl);
                articleService.updateArticle(id, article); // Mettre à jour l'article avec l'URL
                return "Fichier audio téléchargé avec succès : " + audioUrl;
            } else {
                return "Article non trouvé.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors de l'upload du fichier : " + e.getMessage();
        }
    }

    // Endpoint pour accéder à l'audio
    @GetMapping("/audio/{fileName:.+}")
    public byte[] getAudio(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(uploadDir, fileName);
        return Files.readAllBytes(path);
    }
}
