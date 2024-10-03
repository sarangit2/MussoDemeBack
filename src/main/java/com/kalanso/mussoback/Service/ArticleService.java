package com.kalanso.mussoback.Service;


import com.kalanso.mussoback.Model.Article;
import com.kalanso.mussoback.Model.Utilisateur;
import com.kalanso.mussoback.Repository.ArticleRepository;
import com.kalanso.mussoback.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow();
    }

    public Article createArticle(Article article) {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(((UserDetails)currentUser).getUsername()).get();
        article.setUtilisateur(utilisateur);
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article article) {
        Article existing = articleRepository.findById(id).orElseThrow();
        existing.setTitre(article.getTitre());
        existing.setDescription(article.getDescription());
        existing.setType(article.getType());
        existing.setDatePublication(article.getDatePublication());
        return articleRepository.save(existing);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}

