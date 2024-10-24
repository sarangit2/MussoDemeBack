package com.kalanso.mussoback.Controller;

import com.kalanso.mussoback.Model.Categorie;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @GetMapping("/liste")
    public List<String> getCategories() {
        return Arrays.asList(Categorie.values()).stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}

